package modeles;

public class Presence {

    private String employePresence;
    private String datePresence;
    private String heureArrivee;
    private String heureDepart;
    private  StatutPresence statutPresence;

    public Presence(String employePresence, String datePresence, String heureArrivee, String heureDepart, StatutPresence statutPresence) {
        this.employePresence = employePresence;
        this.datePresence = datePresence;
        this.heureArrivee = heureArrivee;
        this.heureDepart = heureDepart;
        this.statutPresence = statutPresence;
    }

    public String getEmployePresence() { return employePresence; }
    public String getDatePresence() { return datePresence; }
    public String getHeureArrivee() { return heureArrivee; }
    public String getHeureDepart() { return heureDepart; }
    public StatutPresence getStatutPresence() { return statutPresence; }

    @Override
    public String toString() {
        return "Matricule: " + employePresence +
               " | Date: " + datePresence +
               " | Arrivée: " + heureArrivee +
               " | Départ: " + heureDepart +
               " | Statut: " + statutPresence;
    }

    
}