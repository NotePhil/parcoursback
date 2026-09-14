package cmr.notep.personnels;

import cmr.notep.commun.AbstractIttest;
import cmr.notep.config.ItTestConfig;
import cmr.notep.exceptions.ParcoursException;
import cmr.notep.impl.PersonnelsService;
import cmr.notep.impl.RolesService;
import cmr.notep.modele.JouerRoles;
import cmr.notep.modele.Personnels;
import cmr.notep.modele.Roles;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Tests de synchronisation des relations de Personnels (notamment roles).
 * Valide les règles métier :
 * - liste absente => purge la relation
 * - création/update de nouvelles relations
 * - suppression des relations non présentes dans la liste
 */
@SpringBootTest(classes = {ItTestConfig.class})
@Transactional()
@Slf4j
public class PersonnelsSyncRelationsTest extends AbstractIttest {

    @Autowired
    PersonnelsService personnelService;
    @Autowired
    RolesService rolesService;
    /**
     * Test 1 : Créer un personnel avec une liste de rôles vide => purge complète
     */
    @Test
    @Order(1)
    @SneakyThrows
    public void testCreatePersonnelWithEmptyRoles() {
        Personnels personnel = Personnels.builder()
                .mail("empty@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .dateCreation(new Date())
                .nom("TestNom")
                .prenom("TestPrenom")
                .sexe("M")
                .dateEntree(new Date())
                .roles(new ArrayList<>()) // liste vide => purge
                .build();

        Personnels saved = personnelService.posterPersonnel(personnel);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertNotNull(saved.getRoles());
        Assertions.assertTrue(saved.getRoles().isEmpty(), "Les rôles doivent être vides après purge");
        log.info("✓ Test 1 réussi : Personnel créé avec rôles purgés");
    }

    /**
     * Test 2 : Créer un personnel avec des rôles, puis purger via update
     */
    @Test
    @Order(2)
    @SneakyThrows
    public void testUpdatePersonnelWithRolePurge() {
        // 1. Créer un personnel avec rôles
        Personnels personnel = Personnels.builder()
                //.id(UUID.randomUUID().toString())
                .mail("withroles@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .dateCreation(new Date())
                .nom("TestNom")
                .prenom("TestPrenom")
                .sexe("F")
                .dateEntree(new Date())
                .roles(List.of(
                        JouerRoles.builder()
                                .role(rolesService.avoirTousRoles().getFirst())
                                .build()
                ))
                .build();

        Personnels saved = personnelService.posterPersonnel(personnel);
        Assertions.assertEquals(1, saved.getRoles().size(), "Doit avoir 1 rôle initialement");

        // 2. Mettre à jour avec liste vide => purge
        saved.setRoles(null); // liste absente => purge
        Personnels updated = personnelService.posterPersonnel(saved);
        Assertions.assertTrue(updated.getRoles() == null || updated.getRoles().isEmpty(),
                "Les rôles doivent être purgés après update avec liste null");
        log.info("✓ Test 2 réussi : Personnel mis à jour avec purge des rôles");
    }

    /**
     * Test 3 : Update partielle de rôles (add/remove/keep)
     */
    @Test
    @Order(3)
    @SneakyThrows
    public void testUpdatePersonnelWithRoleSync() {
        Roles role1 = rolesService.avoirTousRoles().getFirst();
        Roles role2 = rolesService.avoirTousRoles().getLast();
        Roles role3 = rolesService.avoirTousRoles().get(1);
        // 1. Créer un personnel avec 2 rôles (role-1, role-2)
        String personnelId = UUID.randomUUID().toString();
        Personnels personnel = Personnels.builder()
               //
                .mail("sync@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .dateCreation(new Date())
                .nom("SyncNom")
                .prenom("SyncPrenom")
                .sexe("M")
                .dateEntree(new Date())
                .roles(List.of(
                        JouerRoles.builder()
                                //.id(UUID.randomUUID().toString())
                                .role(role1)
                                .build(),
                        JouerRoles.builder()
                                //.id(UUID.randomUUID().toString())
                                .role(role2)
                                .build()
                ))
                .build();

        Personnels saved = personnelService.posterPersonnel(personnel);
        Assertions.assertEquals(2, saved.getRoles().size(), "Doit avoir 2 rôles initialement");

        // 2. Update avec une liste modifiée : garder , supprimer roleExist, ajouter role-3
        JouerRoles roleExist = saved.getRoles().get(0);
        saved.setRoles(List.of(roleExist,
                JouerRoles.builder()
                        .role(role3)
                        .build()
        ));

        Personnels updated = personnelService.posterPersonnel(saved);
        Assertions.assertEquals(2, updated.getRoles().size(),
                "Doit avoir 2 rôles après sync (role-1 kept, role-2 removed, role-3 added)");
        updated.getRoles().forEach(jouerRole -> {
            Assertions.assertTrue(jouerRole.getRole().getId().equals(roleExist.getId())
                            || jouerRole.getRole().getId().equals(role3.getId()),
                    "Les rôles doivent être soit roleExist soit role-3 après sync");
        });
        log.info("✓ Test 3 réussi : Synchronisation partielle des rôles réussie");
    }

    /**
     * Test 4 : Erreur si role inexistant lors de l'update
     */
    @Test
    @Order(4)
    @SneakyThrows
    public void testUpdatePersonnelWithInvalidRole() {
        Personnels personnel = Personnels.builder()
                .mail("invalid@test.fr")
                .telephone("0123456789")
                .adresse("Test Address")
                .dateCreation(new Date())
                .nom("InvalidNom")
                .prenom("InvalidPrenom")
                .sexe("F")
                .dateEntree(new Date())
                .roles(List.of(
                        JouerRoles.builder()
                                .id("invalid-jouer-role-id")  // ID inexistant
                                .role(Roles.builder().id("role-999").build())
                                .build()
                ))
                .build();

        Assertions.assertThrows(RuntimeException.class,
                () -> personnelService.posterPersonnel(personnel),
                "Doit lancer une exception si le JouerRole n'existe pas");
        log.info("✓ Test 4 réussi : Exception levée pour JouerRole inexistant");
    }
}
