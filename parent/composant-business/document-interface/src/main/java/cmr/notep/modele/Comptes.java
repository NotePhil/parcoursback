package cmr.notep.modele;

import java.util.Date;
import java.util.List;

public class Comptes {

    private String id;
    private int solde;
    private String libelle;
    private Date dateCreation;
    private PersonnesPhysique beneficiaire;
    private int montantDecouvertMax;
    private boolean etat;
}