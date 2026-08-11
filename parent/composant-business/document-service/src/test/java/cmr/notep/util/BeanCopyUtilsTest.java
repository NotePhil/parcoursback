package cmr.notep.util;

import org.dozer.DozerBeanMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests - BeanCopyUtils")
class BeanCopyUtilsTest {

    @Test
    @DisplayName("copie uniquement les collections, maps et tableaux en excluant les propriétés nommées")
    void copyPropertiesIncludingOnlyCollectionsDoitCopierLesCollectionsEtExclureLesNoms() {
        SourceBean source = new SourceBean();
        source.setLibelle("source-libelle");
        source.setTags(List.of("alpha", "beta"));
        source.setCodes(new String[]{"A", "B"});
        source.setAttributes(new HashMap<>(Map.of("k1", "v1")));
        source.setExcludedTags(List.of("x", "y"));
        source.setExcludedCodes(new String[]{"Z"});

        TargetBean target = new TargetBean();
        target.setLibelle("target-libelle");
        target.setTags(new ArrayList<>(List.of("old")));
        target.setCodes(new String[]{"OLD"});
        target.setAttributes(new HashMap<>(Map.of("old", "value")));
        target.setExcludedTags(new ArrayList<>(List.of("keep")));
        target.setExcludedCodes(new String[]{"KEEP"});

        BeanCopyUtils.copyPropertiesIncludingOnlyCollections(source, target);

        assertEquals("target-libelle", target.getLibelle(), "Les propriétés scalaires ne doivent pas être copiées");
        assertEquals(List.of("alpha", "beta"), target.getTags(), "La collection doit être copiée");
        assertArrayEquals(new String[]{"A", "B"}, target.getCodes(), "Le tableau doit être copié");
        assertEquals(Map.of("k1", "v1"), target.getAttributes(), "La map doit être copiée");
        assertEquals(List.of("x", "y"), target.getExcludedTags(), "La propriété exclue ne doit pas être copiée");
        assertArrayEquals(new String[]{"Z"}, target.getExcludedCodes(), "Le tableau non exclu doit être copié");
    }

    @Test
    @DisplayName("ignore les valeurs nulles lorsque ignoreNulls vaut true")
    void copyPropertiesIncludingOnlyCollectionsDoitIgnorerLesNullsParDefaut() {
        SourceBean source = new SourceBean();
        source.setTags(null);
        source.setCodes(null);
        source.setAttributes(null);

        TargetBean target = new TargetBean();
        target.setTags(new ArrayList<>(List.of("existing")));
        target.setCodes(new String[]{"existing"});
        target.setAttributes(new HashMap<>(Map.of("existing", "value")));

        BeanCopyUtils.copyPropertiesIncludingOnlyCollections(source, target);

        assertEquals(List.of("existing"), target.getTags());
        assertArrayEquals(new String[]{"existing"}, target.getCodes());
        assertEquals(Map.of("existing", "value"), target.getAttributes());
    }

    @Test
    @DisplayName("ne copie pas les propriétés scalaires même si elles existent dans les deux beans")
    void copyPropertiesIncludingOnlyCollectionsNeDoitPasCopierLesScalaires() {
        SourceBean source = new SourceBean();
        source.setLibelle("source-libelle");
        source.setActive(true);

        TargetBean target = new TargetBean();
        target.setLibelle("target-libelle");
        target.setActive(false);

        BeanCopyUtils.copyPropertiesIncludingOnlyCollections(source, target);

        assertEquals("target-libelle", target.getLibelle());
        assertFalse(target.isActive());
    }

    static class SourceBean {
        private String libelle;
        private boolean active;
        private List<String> tags;
        private String[] codes;
        private Map<String, String> attributes;
        private List<String> excludedTags;
        private String[] excludedCodes;

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String[] getCodes() {
            return codes;
        }

        public void setCodes(String[] codes) {
            this.codes = codes;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        public List<String> getExcludedTags() {
            return excludedTags;
        }

        public void setExcludedTags(List<String> excludedTags) {
            this.excludedTags = excludedTags;
        }

        public String[] getExcludedCodes() {
            return excludedCodes;
        }

        public void setExcludedCodes(String[] excludedCodes) {
            this.excludedCodes = excludedCodes;
        }
    }

    static class TargetBean {
        private String libelle;
        private boolean active;
        private List<String> tags;
        private String[] codes;
        private Map<String, String> attributes;
        private List<String> excludedTags;
        private String[] excludedCodes;

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String[] getCodes() {
            return codes;
        }

        public void setCodes(String[] codes) {
            this.codes = codes;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<String, String> attributes) {
            this.attributes = attributes;
        }

        public List<String> getExcludedTags() {
            return excludedTags;
        }

        public void setExcludedTags(List<String> excludedTags) {
            this.excludedTags = excludedTags;
        }

        public String[] getExcludedCodes() {
            return excludedCodes;
        }

        public void setExcludedCodes(String[] excludedCodes) {
            this.excludedCodes = excludedCodes;
        }
    }

    // ------------------------------------------------------------------
    // Tests pour copyCollectionsWithDozer
    // ------------------------------------------------------------------

    @Test
    @DisplayName("copyCollectionsWithDozer copie la collection vers un attribut cible de même nom")
    void copyCollectionsWithDozerDoitCopierVersAttributDeMemeNom() {
        DocumentModele source = new DocumentModele();
        LigneModele ligne = new LigneModele();
        ligne.setCode("L1");
        source.setLignes(new ArrayList<>(List.of(ligne)));

        DocumentEntitySameName target = new DocumentEntitySameName();

        BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper());

        assertEquals(1, target.getLignes().size());
        assertEquals("L1", target.getLignes().get(0).getCode());
    }

    @Test
    @DisplayName("copyCollectionsWithDozer résout l'attribut cible via la convention nom + \"Entities\"")
    void copyCollectionsWithDozerDoitResoudreParConventionEntities() {
        DocumentModele source = new DocumentModele();
        LigneModele ligne = new LigneModele();
        ligne.setCode("L2");
        source.setLignes(new ArrayList<>(List.of(ligne)));

        DocumentEntityPlural target = new DocumentEntityPlural();

        BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper());

        assertEquals(1, target.getLignesEntities().size());
        assertEquals("L2", target.getLignesEntities().get(0).getCode());
    }

    @Test
    @DisplayName("copyCollectionsWithDozer résout l'attribut cible via l'annotation Dozer @Mapping")
    void copyCollectionsWithDozerDoitResoudreParAnnotationMapping() {
        DocumentModele source = new DocumentModele();
        LigneModele ligne = new LigneModele();
        ligne.setCode("L3");
        source.setLignes(new ArrayList<>(List.of(ligne)));

        DocumentEntityAnnotated target = new DocumentEntityAnnotated();

        BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper());

        assertEquals(1, target.getDetails().size());
        assertEquals("L3", target.getDetails().get(0).getCode());
    }

    @Test
    @DisplayName("copyCollectionsWithDozer vide l'attribut cible existant avant de le remplacer")
    void copyCollectionsWithDozerDoitViderAttributCibleNonVide() {
        DocumentModele source = new DocumentModele();
        LigneModele ligne = new LigneModele();
        ligne.setCode("NOUVEAU");
        source.setLignes(new ArrayList<>(List.of(ligne)));

        DocumentEntityPlural target = new DocumentEntityPlural();
        LigneEntity ancienne = new LigneEntity();
        ancienne.setCode("ANCIEN");
        target.setLignesEntities(new ArrayList<>(List.of(ancienne)));

        BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper());

        assertEquals(1, target.getLignesEntities().size());
        assertEquals("NOUVEAU", target.getLignesEntities().get(0).getCode());
    }

    @Test
    @DisplayName("copyCollectionsWithDozer ignore les propriétés explicitement exclues")
    void copyCollectionsWithDozerDoitIgnorerLesProprietesExclues() {
        DocumentModele source = new DocumentModele();
        LigneModele ligne = new LigneModele();
        ligne.setCode("IGNORE");
        source.setLignes(new ArrayList<>(List.of(ligne)));

        DocumentEntityPlural target = new DocumentEntityPlural();
        LigneEntity conservee = new LigneEntity();
        conservee.setCode("CONSERVEE");
        target.setLignesEntities(new ArrayList<>(List.of(conservee)));

        BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper(), "lignes");

        assertEquals(1, target.getLignesEntities().size());
        assertEquals("CONSERVEE", target.getLignesEntities().get(0).getCode(),
                "La propriété exclue ne doit pas être copiée");
    }

    @Test
    @DisplayName("copyCollectionsWithDozer ignore une collection source nulle")
    void copyCollectionsWithDozerDoitIgnorerCollectionSourceNull() {
        DocumentModele source = new DocumentModele();
        source.setLignes(null);

        DocumentEntityPlural target = new DocumentEntityPlural();
        List<LigneEntity> existing = new ArrayList<>(List.of(new LigneEntity()));
        target.setLignesEntities(existing);

        BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper());

        assertSame(existing, target.getLignesEntities(), "La collection cible ne doit pas être modifiée si la source est null");
    }

    @Test
    @DisplayName("copyCollectionsWithDozer lève une exception si aucun attribut cible ne correspond")
    void copyCollectionsWithDozerDoitLeverExceptionSiAttributCibleIntrouvable() {
        DocumentModele source = new DocumentModele();
        LigneModele ligne = new LigneModele();
        ligne.setCode("X");
        source.setLignes(new ArrayList<>(List.of(ligne)));

        DocumentEntityNoMatch target = new DocumentEntityNoMatch();

        BeanCollectionMappingException exception = assertThrows(BeanCollectionMappingException.class,
                () -> BeanCopyUtils.copyCollectionsWithDozer(source, target, new DozerBeanMapper()));
        assertTrue(exception.getMessage().contains("lignes"));
    }
}


