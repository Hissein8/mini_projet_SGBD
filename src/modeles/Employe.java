package modeles;

import java.sql.Date;

public class Employe {
    private String matricule;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private Date dateEmbauche;
    private String email;
    private String telephone;
    private String departementCode;
    private String posteCode;

    // Constructeur
    public Employe(String matricule, String nom, String prenom, Date dateNaissance, 
                   Date dateEmbauche, String email, String telephone, String departementCode, String posteCode) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.dateEmbauche = dateEmbauche;
        this.email = email;
        this.telephone = telephone;
        this.departementCode = departementCode;
        this.posteCode = posteCode;
    }

    // Getters et Setters
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
    public Date getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(Date dateEmbauche) { this.dateEmbauche = dateEmbauche; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getDepartementCode() { return departementCode; }
    public void setDepartementCode(String departementCode) { this.departementCode = departementCode; }
    public String getPosteCode() { return posteCode; }
    public void setPosteCode(String posteCode) { this.posteCode = posteCode; }
}