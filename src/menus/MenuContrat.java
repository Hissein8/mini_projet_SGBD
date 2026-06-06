package menus;

import connexion.Connexion;
import java.sql.*;
import java.util.Scanner;

public class MenuContrat {
    public static void afficherMenu(Scanner scanner) {
        int choix;
        do {
            System.out.println("\n--- GESTION DES CONTRATS ---");
            System.out.println("1. Lister les CDD expirant dans moins de 30 jours");
            System.out.println("2. Afficher les employes sans contrat actif");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: listerCDDExpirants(); break;
                case 2: afficherEmployesSansContrat(); break;
                case 3: System.out.println("Retour..."); break;
                default: System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }

    private static void listerCDDExpirants() {
        System.out.println("\n-- CDD expirant dans moins de 30 jours --");
        String sql = "SELECT e.matricule, e.nom_emp, e.prenom_emp, c.date_fin_contrat " +
                     "FROM Employe e JOIN Contrat c ON e.matricule = c.employe_contrat " +
                     "WHERE c.type_contrat = 'CDD' AND DATEDIFF(c.date_fin_contrat, NOW()) BETWEEN 0 AND 30";

        try (Connection conn = Connexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean trouve = false;
            while (rs.next()) {
                trouve = true;
                System.out.printf("Matricule : %s | %s %s | Expire le : %s\n",
                        rs.getString("matricule"), rs.getString("prenom_emp"),
                        rs.getString("nom_emp"), rs.getDate("date_fin_contrat"));
            }
            if (!trouve) System.out.println("Aucun CDD n'expire dans les 30 prochains jours.");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    private static void afficherEmployesSansContrat() {
        System.out.println("\n-- Employes sans contrat actif --");
        String sql = "SELECT matricule, nom_emp, prenom_emp FROM Employe " +
                     "WHERE matricule NOT IN (" +
                     "  SELECT employe_contrat FROM Contrat " +
                     "  WHERE NOW() BETWEEN date_debut_contrat AND IFNULL(date_fin_contrat, '2099-12-31')" +
                     ")";

        try (Connection conn = Connexion.getConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean trouve = false;
            while (rs.next()) {
                trouve = true;
                System.out.printf("Matricule : %s | %s %s\n",
                        rs.getString("matricule"), rs.getString("prenom_emp"), rs.getString("nom_emp"));
            }
            if (!trouve) System.out.println("Tous les employes ont un contrat actif.");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}