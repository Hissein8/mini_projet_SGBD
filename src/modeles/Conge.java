package modeles;

public class Conge {
    private String employeConge;
    private String dateDebut;
    private String dateFin;
    private TypeConge typeConge;
    private String statutConge;

    public Conge(String employeConge, String dateDebut, String dateFin, TypeConge typeConge, String statutConge) {
        this.employeConge = employeConge;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.typeConge = typeConge;
        this.statutConge = statutConge;
    }

    public String getEmployeConge() { return employeConge; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public TypeConge getTypeConge() { return typeConge; }
    public String getStatutConge() { return statutConge; }

    @Override
    public String toString() {
        return "Matricule: " + employeConge +
               " | Du: " + dateDebut +
               " | Au: " + dateFin +
               " | Type: " + typeConge +
               " | Statut: " + statutConge;
    }
}