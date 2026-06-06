package modeles;

public class Bulletin {
    private String employeBulletin; // Correspond à employe_bulletin char(8)
    private int moisBulletin;       // Correspond à mois_bulletin int
    private int anneeBulletin;      // Correspond à annee_bulletin int
    private long salaireBrut;       // Correspond à salaire_brut bigint
    private int nbJoursTravailles;  // Correspond à nb_jours_travailles int
    private int nbAbsencesInjustifiees; // Correspond à nb_absences_injustifiees int
    private long retenues;          // Correspond à retenues bigint
    private long salaireNet;        // Correspond à salaire_net bigint

    // Constructeur vide
    public Bulletin() {}

    // Constructeur complet
    public Bulletin(String employeBulletin, int moisBulletin, int anneeBulletin, long salaireBrut, 
                    int nbJoursTravailles, int nbAbsencesInjustifiees, long retenues, long salaireNet) {
        this.employeBulletin = employeBulletin;
        this.moisBulletin = moisBulletin;
        this.anneeBulletin = anneeBulletin;
        this.salaireBrut = salaireBrut;
        this.nbJoursTravailles = nbJoursTravailles;
        this.nbAbsencesInjustifiees = nbAbsencesInjustifiees;
        this.retenues = retenues;
        this.salaireNet = salaireNet;
    }

    // Getters et Setters
    public String getEmployeBulletin() { return employeBulletin; }
    public void setEmployeBulletin(String employeBulletin) { this.employeBulletin = employeBulletin; }

    public int getMoisBulletin() { return moisBulletin; }
    public void setMoisBulletin(int moisBulletin) { this.moisBulletin = moisBulletin; }

    public int getAnneeBulletin() { return anneeBulletin; }
    public void setAnneeBulletin(int anneeBulletin) { this.anneeBulletin = anneeBulletin; }

    public long getSalaireBrut() { return salaireBrut; }
    public void setSalaireBrut(long salaireBrut) { this.salaireBrut = salaireBrut; }

    public int getNbJoursTravailles() { return nbJoursTravailles; }
    public void setNbJoursTravailles(int nbJoursTravailles) { this.nbJoursTravailles = nbJoursTravailles; }

    public int getNbAbsencesInjustifiees() { return nbAbsencesInjustifiees; }
    public void setNbAbsencesInjustifiees(int nbAbsencesInjustifiees) { this.nbAbsencesInjustifiees = nbAbsencesInjustifiees; }

    public long getRetenues() { return retenues; }
    public void setRetenues(long retenues) { this.retenues = retenues; }

    public long getSalaireNet() { return salaireNet; }
    public void setSalaireNet(long salaireNet) { this.salaireNet = salaireNet; }
}