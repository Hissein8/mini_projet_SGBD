package modeles;

import java.sql.Date;

public class Contrat {
    private int id; // clé primaire auto-incrémentée supposée
    private String employeMatricule;
    private String type; // CDI, CDD, Stage
    private Date dateDebut;
    private Date dateFin;
    private double salaireBrut;

    // Constructeur
    public Contrat(String employeMatricule, String type, Date dateDebut, Date dateFin, double salaireBrut) {
        this.employeMatricule = employeMatricule;
        this.type = type;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.salaireBrut = salaireBrut;
    }

    // Getters et Setters
    public String getEmployeMatricule() { return employeMatricule; }
    public String getType() { return type; }
    public Date getDateDebut() { return dateDebut; }
    public Date getDateFin() { return dateFin; }
    public double getSalaireBrut() { return salaireBrut; }
}