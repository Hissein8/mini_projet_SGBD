package menus;

import connexion.Connexion;
import java.sql.*;
import java.util.Scanner;
import modeles.StatutPresence;

public class MenuPresence {
    private Scanner scanner = new Scanner(System.in);

    public void afficherMenu() {
        int choix = -1;
        while (choix != 0) {
            System.out.println("\n===== MENUPRESENCES =====");
            System.out.println("1. Enregistrer les présences d'une journée pour un département");
            System.out.println("2. Afficher les présences d'un employé");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1: enregistrerPresencesDepartement(); break;
                case 2: afficherPresencesEmploye(); break;
                case 0: System.out.println("Retour au menu principal..."); break;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private void enregistrerPresencesDepartement() {
        try (Connection conn = Connexion.getConnexion()) {
            System.out.print("Code département (RH, IT, FIN, MAR) : ");
            String codeDpt = scanner.nextLine().toUpperCase();
            System.out.print("Date (YYYY-MM-DD) : ");
            String date = scanner.nextLine();

            // Récupérer tous les employés du département
            String sql = "SELECT matricule, nom_emp, prenom_emp FROM Employe WHERE code_dpt = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, codeDpt);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String matricule = rs.getString("matricule");
                String nom = rs.getString("nom_emp");
                String prenom = rs.getString("prenom_emp");

                System.out.println("\nEmployé : " + prenom + " " + nom + " (" + matricule + ")");
                System.out.println("Statut : ");
                System.out.println("  1. present");
                System.out.println("  2. absent");
                System.out.println("  3. conge");
                System.out.print("Votre choix (1/2/3) : ");
                int choixStatut = Integer.parseInt(scanner.nextLine());
                StatutPresence statut;
                switch (choixStatut) {
                    case 1: statut = StatutPresence.present; break;
                    case 2: statut = StatutPresence.absent; break;
                    case 3: statut = StatutPresence.conge; break;
                    default:
                    System.out.println("❌ Choix invalide, absent par défaut.");
                    statut = StatutPresence.absent;
                }

                String heureArrivee = "00:00:00";
                String heureDepart = "00:00:00";

                if (statut.equals("present")) {
                    System.out.print("Heure d'arrivée (HH:MM) : ");
                    heureArrivee = scanner.nextLine() + ":00";
                    System.out.print("Heure de départ (HH:MM) : ");
                    heureDepart = scanner.nextLine() + ":00";
                }

                // Vérifier si une présence existe déjà pour ce jour
                String checkSql = "SELECT COUNT(*) FROM Presence WHERE employe_presence = ? AND date_presence = ?";
                PreparedStatement checkPs = conn.prepareStatement(checkSql);
                checkPs.setString(1, matricule);
                checkPs.setString(2, date);
                ResultSet checkRs = checkPs.executeQuery();
                checkRs.next();

                if (checkRs.getInt(1) > 0) {
                    System.out.println("⚠ Présence déjà enregistrée pour " + prenom + " " + nom + " ce jour-là. Ignoré.");
                    continue;
                }

                String insertSql = "INSERT INTO Presence (employe_presence, date_presence, heure_arrivee, heure_depart, statut_presence) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertPs = conn.prepareStatement(insertSql);
                insertPs.setString(1, matricule);
                insertPs.setString(2, date);
                insertPs.setString(3, heureArrivee);
                insertPs.setString(4, heureDepart);
                insertPs.setString(5, statut.name());
                insertPs.executeUpdate();
                System.out.println("✔ Présence enregistrée.");
            }

            System.out.println("\n✔ Toutes les présences du département ont été enregistrées.");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    private void afficherPresencesEmploye() {
        try (Connection conn = Connexion.getConnexion()) {
            System.out.print("Matricule de l'employé : ");
            String matricule = scanner.nextLine().toUpperCase();

            String sql = "SELECT date_presence, heure_arrivee, heure_depart, statut_presence " +
                         "FROM Presence WHERE employe_presence = ? ORDER BY date_presence DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, matricule);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Présences de " + matricule + " ---");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("Date: " + rs.getString("date_presence") +
                                   " | Arrivée: " + rs.getString("heure_arrivee") +
                                   " | Départ: " + rs.getString("heure_depart") +
                                   " | Statut: " + rs.getString("statut_presence"));
            }
            if (!found) System.out.println("Aucune présence trouvée.");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
