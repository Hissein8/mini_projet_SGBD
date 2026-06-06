package menus;

import connexion.Connexion;
import java.sql.*;
import java.util.Scanner;

public class MenuEmploye {
    public static void afficherMenu(Scanner scanner) {
        int choix = 0;
        do {
            System.out.println("\n--- GESTION DES EMPLOYES ---");
            System.out.println("1. Ajouter un employe");
            System.out.println("2. Modifier un employe (Email & Telephone)");
            System.out.println("3. Afficher la fiche complete d'un employe");
            System.out.println("4. Retour au menu principal");
            System.out.print("Votre choix : ");

            try {
                choix = scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre (1, 2, 3 ou 4) !");
                scanner.nextLine();
                continue;
            }
            scanner.nextLine();

            switch (choix) {
                case 1: ajouterEmploye(scanner); break;
                case 2: modifierEmploye(scanner); break;
                case 3: afficherFicheComplete(scanner); break;
                case 4: System.out.println("Retour..."); break;
                default: System.out.println("Choix invalide ! Entrez 1, 2, 3 ou 4.");
            }
        } while (choix != 4);
    }

    private static void ajouterEmploye(Scanner scanner) {
        System.out.println("\n-- Ajouter un Employe --");
        System.out.println("Departements disponibles : RH, IT, FIN, MAR");
        System.out.println("Postes disponibles : DIR01, DEV01, DEV02, RH01, COMP1, MKT01, SYS01, ANA01");
        System.out.print("Matricule (8 car. ex: EMP00021) : "); String mat = scanner.nextLine();
        System.out.print("Nom : "); String nom = scanner.nextLine();
        System.out.print("Prenom : "); String prenom = scanner.nextLine();
        System.out.print("Date de naissance (AAAA-MM-JJ) : "); String dns = scanner.nextLine();
        System.out.print("Date d'embauche (AAAA-MM-JJ) : "); String dem = scanner.nextLine();
        System.out.print("Email : "); String email = scanner.nextLine();
        System.out.print("Telephone : "); String tel = scanner.nextLine();
        System.out.print("Code Departement : "); String codeDep = scanner.nextLine();
        System.out.print("Code Poste : "); String codePoste = scanner.nextLine();

        String sql = "INSERT INTO Employe (matricule, nom_emp, prenom_emp, date_naissance, date_embauche, email, telephone, code_dpt, code_poste) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Connexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mat);
            pstmt.setString(2, nom);
            pstmt.setString(3, prenom);
            pstmt.setDate(4, Date.valueOf(dns));
            pstmt.setDate(5, Date.valueOf(dem));
            pstmt.setString(6, email);
            pstmt.setString(7, tel);
            pstmt.setString(8, codeDep);
            pstmt.setString(9, codePoste);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Employe ajoute avec succes !");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Format de date invalide ! Utilisez AAAA-MM-JJ.");
        }
    }

    private static void modifierEmploye(Scanner scanner) {
        System.out.println("\n-- Modifier un Employe --");
        System.out.print("Matricule de l'employe a modifier : ");
        String mat = scanner.nextLine();
        System.out.print("Nouvel Email : "); String email = scanner.nextLine();
        System.out.print("Nouveau Telephone : "); String tel = scanner.nextLine();

        String sql = "UPDATE Employe SET email = ?, telephone = ? WHERE matricule = ?";

        try (Connection conn = Connexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, tel);
            pstmt.setString(3, mat);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Employe mis a jour avec succes !");
            else System.out.println("Employe introuvable.");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    private static void afficherFicheComplete(Scanner scanner) {
        System.out.print("\nEntrez le matricule de l'employe (ex: EMP00001) : ");
        String mat = scanner.nextLine();

        try (Connection conn = Connexion.getConnexion()) {

            // 1. Informations de base
            String sqlEmp = "SELECT e.*, d.nom_dpt, p.intitule FROM Employe e " +
                            "JOIN Departement d ON e.code_dpt = d.code_dpt " +
                            "JOIN Poste p ON e.code_poste = p.code_poste " +
                            "WHERE e.matricule = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlEmp)) {
                pstmt.setString(1, mat);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("\n========= FICHE EMPLOYE =========");
                    System.out.println("Matricule   : " + rs.getString("matricule"));
                    System.out.println("Nom complet : " + rs.getString("prenom_emp") + " " + rs.getString("nom_emp"));
                    System.out.println("Email       : " + rs.getString("email"));
                    System.out.println("Telephone   : " + rs.getString("telephone"));
                    System.out.println("Departement : " + rs.getString("nom_dpt"));
                    System.out.println("Poste       : " + rs.getString("intitule"));
                    System.out.println("==================================");
                } else {
                    System.out.println("Employe introuvable. Verifiez le matricule (ex: EMP00001).");
                    return;
                }
            }

            // 2. Contrats
            System.out.println("\n[Contrats]");
            String sqlContrat = "SELECT * FROM Contrat WHERE employe_contrat = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlContrat)) {
                pstmt.setString(1, mat);
                ResultSet rs = pstmt.executeQuery();
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("- Type : %s | Debut : %s | Fin : %s | Brut : %d FCFA\n",
                            rs.getString("type_contrat"),
                            rs.getDate("date_debut_contrat"),
                            rs.getDate("date_fin_contrat"),
                            rs.getLong("salaire_brut_contrat"));
                }
                if (!found) System.out.println("Aucun contrat.");
            }

            // 3. Presences recentes
            System.out.println("\n[5 Dernieres Presences]");
            String sqlPres = "SELECT date_presence, statut_presence FROM Presence " +
                             "WHERE employe_presence = ? ORDER BY date_presence DESC LIMIT 5";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlPres)) {
                pstmt.setString(1, mat);
                ResultSet rs = pstmt.executeQuery();
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("- Date : %s | Statut : %s\n",
                            rs.getDate("date_presence"),
                            rs.getString("statut_presence"));
                }
                if (!found) System.out.println("Aucune presence enregistree.");
            }

            // 4. Conges
            System.out.println("\n[Conges]");
            String sqlConge = "SELECT * FROM Conge WHERE employe_conge = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlConge)) {
                pstmt.setString(1, mat);
                ResultSet rs = pstmt.executeQuery();
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.printf("- Du %s au %s | Type : %s | Statut : %s\n",
                            rs.getDate("date_debut_conge"),
                            rs.getDate("date_fin_conge"),
                            rs.getString("type_conge"),
                            rs.getString("statut_conge"));
                }
                if (!found) System.out.println("Aucun conge.");
            }
            System.out.println("==================================");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}