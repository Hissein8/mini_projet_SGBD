package menus;

import connexion.Connexion;
import java.sql.*;
import java.util.Scanner;
import modeles.TypeConge;

public class MenuConge {

    public static void afficherMenu(Scanner scanner) {
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n===== MENU CONGÉS =====");
            System.out.println("1. Soumettre une demande de congé");
            System.out.println("2. Approuver ou refuser un congé");
            System.out.println("3. Afficher le solde de congés d'un employé");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1: soumettreDemandeConge(scanner); break;
                case 2: traiterDemandeConge(scanner); break;
                case 3: afficherSoldeConges(scanner); break;
                case 0: System.out.println("Retour au menu principal..."); break;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void soumettreDemandeConge(Scanner scanner) {
        try (Connection conn = Connexion.getConnexion()) {
            System.out.print("Matricule de l'employé : ");
            String matricule = scanner.nextLine().toUpperCase();

            // Vérifier que l'employé existe
            String checkEmp = "SELECT COUNT(*) FROM Employe WHERE matricule = ?";
            PreparedStatement psCheck = conn.prepareStatement(checkEmp);
            psCheck.setString(1, matricule);
            ResultSet rsCheck = psCheck.executeQuery();
            rsCheck.next();
            if (rsCheck.getInt(1) == 0) {
                System.out.println("Employé introuvable.");
                return;
            }

            System.out.print("Date de début (YYYY-MM-DD) : ");
            String dateDebut = scanner.nextLine();
            System.out.print("Date de fin (YYYY-MM-DD) : ");
            String dateFin = scanner.nextLine();
            System.out.println("Type de congé : ");
            System.out.println("  1. annuel");
            System.out.println("  2. maladie");
            System.out.println("  3. maternite");
            System.out.print("Votre choix (1/2/3) : ");
            int choixType = Integer.parseInt(scanner.nextLine());
            TypeConge type;
            switch (choixType) {
                case 1: type = TypeConge.annuel; break;
                case 2: type = TypeConge.maladie; break;
                case 3: type = TypeConge.maternite; break;
                default:
                System.out.println("Choix invalide, annuel par défaut.");
                type = TypeConge.annuel;
            }

            String sql = "INSERT INTO Conge (employe_conge, date_debut_conge, date_fin_conge, type_conge, statut_conge) " +
                         "VALUES (?, ?, ?, ?, 'demande')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, matricule);
            ps.setString(2, dateDebut);
            ps.setString(3, dateFin);
            ps.setString(4, type.name());
            ps.executeUpdate();

            System.out.println("Demande de congé soumise avec succès (statut : demande).");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    private static void traiterDemandeConge(Scanner scanner) {
        try (Connection conn = Connexion.getConnexion()) {

            // Afficher les demandes en attente
            String listeSql = "SELECT c.employe_conge, e.nom_emp, e.prenom_emp, " +
                              "c.date_debut_conge, c.date_fin_conge, c.type_conge " +
                              "FROM Conge c JOIN Employe e ON c.employe_conge = e.matricule " +
                              "WHERE c.statut_conge = 'demande'";
            PreparedStatement listPs = conn.prepareStatement(listeSql);
            ResultSet listRs = listPs.executeQuery();

            System.out.println("\n--- Demandes de congé en attente ---");
            boolean found = false;
            while (listRs.next()) {
                found = true;
                System.out.println("Matricule: " + listRs.getString("employe_conge") +
                                   " | " + listRs.getString("prenom_emp") + " " + listRs.getString("nom_emp") +
                                   " | Du: " + listRs.getString("date_debut_conge") +
                                   " | Au: " + listRs.getString("date_fin_conge") +
                                   " | Type: " + listRs.getString("type_conge"));
            }
            if (!found) {
                System.out.println("Aucune demande en attente.");
                return;
            }

            System.out.print("\nMatricule de l'employé à traiter : ");
            String matricule = scanner.nextLine().toUpperCase();
            System.out.print("Date de début du congé à traiter (YYYY-MM-DD) : ");
            String dateDebut = scanner.nextLine();
            System.out.print("Décision (approuve/refuse) : ");
            String decision = scanner.nextLine().toLowerCase();

            if (!decision.equals("approuve") && !decision.equals("refuse")) {
                System.out.println("Décision invalide. Tapez 'approuve' ou 'refuse'.");
                return;
            }

            String updateSql = "UPDATE Conge SET statut_conge = ? " +
                               "WHERE employe_conge = ? AND date_debut_conge = ? AND statut_conge = 'demande'";
            PreparedStatement updatePs = conn.prepareStatement(updateSql);
            updatePs.setString(1, decision);
            updatePs.setString(2, matricule);
            updatePs.setString(3, dateDebut);
            int rows = updatePs.executeUpdate();

            if (rows > 0) {
                System.out.println("✔ Congé " + decision + " avec succès.");
            } else {
                System.out.println("Aucune demande trouvée pour ces informations.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    private static void afficherSoldeConges(Scanner scanner) {
        try (Connection conn = Connexion.getConnexion()) {
            System.out.print("Matricule de l'employé : ");
            String matricule = scanner.nextLine().toUpperCase();

            // Vérifier que l'employé existe
            String checkSql = "SELECT nom_emp, prenom_emp FROM Employe WHERE matricule = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, matricule);
            ResultSet checkRs = checkPs.executeQuery();

            if (!checkRs.next()) {
                System.out.println("Employé introuvable.");
                return;
            }

            String nom = checkRs.getString("nom_emp");
            String prenom = checkRs.getString("prenom_emp");

            String sql = "SELECT 30 - IFNULL(SUM(DATEDIFF(date_fin_conge, date_debut_conge) + 1), 0) AS solde " +
                         "FROM Conge " +
                         "WHERE employe_conge = ? " +
                         "AND statut_conge = 'approuve' " +
                         "AND type_conge = 'annuel' " +
                         "AND YEAR(date_debut_conge) = YEAR(CURDATE())";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, matricule);
            ResultSet rs = ps.executeQuery();
            rs.next();

            System.out.println("\n--- Solde de congés ---");
            System.out.println("Employé : " + prenom + " " + nom);
            System.out.println("Solde congés annuels restants : " + rs.getInt("solde") + " jours");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
