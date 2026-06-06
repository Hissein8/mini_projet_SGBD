package menus;

import connexion.Connexion;
import java.sql.*;
import java.util.Scanner;

public class MenuBulletin {
    private static Scanner scanner = new Scanner(System.in);

    public static void afficherMenu() {
        int choix;
        do {
            System.out.println("\n=== GESTION DES BULLETINS DE PAIE ===");
            System.out.println("1. Générer un bulletin de paie");
            System.out.println("2. Afficher les bulletins d'un employé");
            System.out.println("3. Retour au menu principal");
            System.out.print("Votre choix : ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez saisir un nombre valide !");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Vider le tampon

            switch (choix) {
                case 1:
                    genererBulletin();
                    break;
                case 2:
                    afficherBulletinsEmploye();
                    break;
                case 3:
                    System.out.println("Retour...");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }

    private static void genererBulletin() {
        System.out.print("Matricule de l'employé : ");
        String matricule = scanner.nextLine();
        System.out.print("Mois (1-12) : ");
        int mois = scanner.nextInt();
        System.out.print("Année : ");
        int annee = scanner.nextInt();
        System.out.print("Nombre de jours travaillés : ");
        int joursTravailles = scanner.nextInt();
        scanner.nextLine(); // Vider le tampon

        try (Connection conn = Connexion.getConnexion()) {
            if (conn == null) {
                System.out.println("Erreur : Impossible de se connecter à la base de données.");
                return;
            }

            // 1. Récupérer le salaire brut du contrat actif pour ce mois/année
            String queryContrat = "SELECT salaire_brut_contrat FROM Contrat WHERE employe_contrat = ? " +
                                  "AND date_debut_contrat <= ? AND (date_fin_contrat >= ? OR date_fin_contrat IS NULL)";
            
            double salaireBrut = 0;
            String datePivot = annee + "-" + String.format("%02d", mois) + "-28";

            try (PreparedStatement psContrat = conn.prepareStatement(queryContrat)) {
                psContrat.setString(1, matricule);
                psContrat.setString(2, datePivot);
                psContrat.setString(3, datePivot);
                
                try (ResultSet rsContrat = psContrat.executeQuery()) {
                    if (!rsContrat.next()) {
                        System.out.println("Erreur : Aucun contrat actif trouvé pour cet employé à cette période.");
                        return;
                    }
                    salaireBrut = rsContrat.getDouble("salaire_brut_contrat");
                }
            }

            // 2. Compter les absences qui ne sont PAS couvertes par un congé approuvé
            String queryAbsences = 
                "SELECT COUNT(*) AS total_absences FROM Presence p " +
                "WHERE p.employe_presence = ? " +
                "AND p.statut_presence = 'absent' " +
                "AND MONTH(p.date_presence) = ? " +
                "AND YEAR(p.date_presence) = ? " +
                "AND NOT EXISTS (" +
                "    SELECT 1 FROM Conge c " +
                "    WHERE c.employe_conge = p.employe_presence " +
                "    AND c.statut_conge = 'approuve' " +
                "    AND p.date_presence BETWEEN c.date_debut_conge AND c.date_fin_conge" +
                ")";

            int absencesInjustifiees = 0;
            try (PreparedStatement psAbsences = conn.prepareStatement(queryAbsences)) {
                psAbsences.setString(1, matricule);
                psAbsences.setInt(2, mois);
                psAbsences.setInt(3, annee);
                
                try (ResultSet rsAbsences = psAbsences.executeQuery()) {
                    if (rsAbsences.next()) {
                        absencesInjustifiees = rsAbsences.getInt("total_absences");
                    }
                }
            }

            // 3. Calculs automatiques
            double retenues = (salaireBrut / 22.0) * absencesInjustifiees;
            double salaireNet = salaireBrut - retenues;
            if (salaireNet < 0) salaireNet = 0;

            // 4. Insertion du bulletin généré
            String queryInsert = "INSERT INTO Bulletin (employe_bulletin, mois_bulletin, annee_bulletin, salaire_brut, " +
                                 "nb_jours_travailles, nb_absences_injustifiees, retenues, salaire_net) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement psInsert = conn.prepareStatement(queryInsert)) {
                psInsert.setString(1, matricule);
                psInsert.setInt(2, mois);
                psInsert.setInt(3, annee);
                psInsert.setDouble(4, salaireBrut);
                psInsert.setInt(5, joursTravailles);
                psInsert.setInt(6, absencesInjustifiees);
                psInsert.setDouble(7, retenues);
                psInsert.setDouble(8, salaireNet);

                int rows = psInsert.executeUpdate();
                if (rows > 0) {
                    System.out.println("\n=============================================");
                    System.out.println("Bulletin généré avec succès !");
                    System.out.printf("   Salaire Brut : %,.2f FCFA\n", salaireBrut);
                    System.out.println("   Absences Injustifiées : " + absencesInjustifiees);
                    System.out.printf("   Retenues : %,.2f FCFA\n", retenues);
                    System.out.printf("   Salaire Net : %,.2f FCFA\n", salaireNet);
                    System.out.println("=============================================");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la génération du bulletin : " + e.getMessage());
        }
    }

    private static void afficherBulletinsEmploye() {
        System.out.print("Matricule de l'employé : ");
        String matricule = scanner.nextLine();

        String query = "SELECT * FROM Bulletin WHERE employe_bulletin = ? ORDER BY annee_bulletin DESC, mois_bulletin DESC";
        
        try (Connection conn = Connexion.getConnexion()) {
            if (conn == null) {
                System.out.println("Erreur : Impossible de se connecter à la base de données.");
                return;
            }
            
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, matricule);
                
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("\n--- Historique des bulletins pour l'employé " + matricule + " ---");
                    boolean trouve = false;
                    
                    while (rs.next()) {
                        trouve = true;
                        System.out.printf("Période : %02d/%d | Brut : %,.2f | Absences : %d | Retenues : %,.2f | Net : %,.2f FCFA\n",
                                rs.getInt("mois_bulletin"),
                                rs.getInt("annee_bulletin"),
                                rs.getDouble("salaire_brut"),
                                rs.getInt("nb_absences_injustifiees"),
                                rs.getDouble("retenues"),
                                rs.getDouble("salaire_net"));
                    }
                    if (!trouve) {
                        System.out.println("Aucun bulletin trouvé pour cet employé.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}