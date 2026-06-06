package menus;

import connexion.Connexion;
import java.sql.*;

public class MenuTableauBord {
    
    public static void afficherMenu() {
        System.out.println("\n=== TABLEAU DE BORD RH ===");
        
        try (Connection conn = Connexion.getConnexion()) {
            if (conn == null) {
                System.out.println("Erreur : Impossible de se connecter à la base de données.");
                return;
            }

            afficherMasseSalariale(conn);
            afficherTauxAbsenteisme(conn);
            afficherCongesEnAttente(conn);
            
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }

    // 1. Masse salariale mensuelle par département (GROUP BY + SUM)
    private static void afficherMasseSalariale(Connection conn) {
        // System.out.println("\n💰 [Masse Salariale du Mois par Département]");
        System.out.println("\n[Masse Salariale du Mois par Département]");
        String query = "SELECT d.nom_dpt AS dept_nom, SUM(b.salaire_brut) AS total_masse " +
                       "FROM Bulletin b " +
                       "JOIN Employe e ON b.employe_bulletin = e.matricule " +
                       "JOIN Departement d ON e.code_dpt = d.code_dpt " +
                       "WHERE b.mois_bulletin = MONTH(CURDATE()) AND b.annee_bulletin = YEAR(CURDATE()) " +
                       "GROUP BY d.code_dpt, d.nom_dpt";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            boolean data = false;
            while (rs.next()) {
                data = true;
                System.out.printf("   - %s : %,.2f FCFA\n", rs.getString("dept_nom"), rs.getDouble("total_masse"));
            }
            if (!data) System.out.println("   Aucune donnée de paie pour le mois en cours.");
        } catch (SQLException e) {
            System.out.println("   Erreur requete masse salariale : " + e.getMessage());
        }
    }

    // 2. Taux d'absentéisme par département sur les 3 derniers mois (Basé sur la requête 6 du script SQL)
    private static void afficherTauxAbsenteisme(Connection conn) {
        System.out.println("\n[Taux d'absentéisme par Département (3 derniers mois)]");
        // System.out.println("\n📉 [Taux d'absentéisme par Département (3 derniers mois)]");
        String query = "SELECT d.nom_dpt AS dept_nom, " +
                       "ROUND((SUM(b.nb_absences_injustifiees) / SUM(b.nb_jours_travailles + b.nb_absences_injustifiees)) * 100, 2) AS taux_absenteisme " +
                       "FROM Bulletin b " +
                       "JOIN Employe e ON b.employe_bulletin = e.matricule " +
                       "JOIN Departement d ON e.code_dpt = d.code_dpt " +
                       "WHERE STR_TO_DATE(CONCAT(b.annee_bulletin, '-', b.mois_bulletin, '-01'), '%Y-%m-%d') " +
                       "BETWEEN DATE_SUB(CURDATE(), INTERVAL 3 MONTH) AND CURDATE() " +
                       "GROUP BY d.code_dpt, d.nom_dpt";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            boolean data = false;
            while (rs.next()) {
                data = true;
                System.out.printf("   - %s : %.2f%%\n", rs.getString("dept_nom"), rs.getDouble("taux_absenteisme"));
            }
            if (!data) System.out.println("   Aucun enregistrement de présence trouvé pour les 3 derniers mois.");
        } catch (SQLException e) {
            System.out.println("   Erreur requete absentéisme : " + e.getMessage());
        }
    }

    // 3. Congés en attente d'approbation
    private static void afficherCongesEnAttente(Connection conn) {
        // System.out.println("\n📅 [Demandes de Congés en Attente d'Approbation]");
        System.out.println("\n[Demandes de Congés en Attente d'Approbation]");
        String query = "SELECT e.nom_emp, e.prenom_emp, c.date_debut_conge, c.date_fin_conge, c.type_conge " +
                       "FROM Conge c " +
                       "JOIN Employe e ON c.employe_conge = e.matricule " +
                       "WHERE c.statut_conge = 'demande'";
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            boolean data = false;
            while (rs.next()) {
                data = true;
                System.out.printf("   - %s %s | Du %s au %s (%s)\n", 
                        rs.getString("prenom_emp"), rs.getString("nom_emp"),
                        rs.getDate("date_debut_conge"), rs.getDate("date_fin_conge"), rs.getString("type_conge"));
            }
            // if (!data) System.out.println("   🎉 Aucune demande de congé en attente.");
            if (!data) System.out.println("   Aucune demande de congé en attente.");
        } catch (SQLException e) {
            System.out.println("   Erreur requete congés en attente : " + e.getMessage());
        }
    }
}