import connexion.Connexion;
import java.sql.Connection;
import java.util.Scanner;
import menus.MenuContrat;
import menus.MenuEmploye;

public class Main {
    public static void main(String[] args) {
        Connection conn = Connexion.getConnexion();
        if (conn == null) {
            System.out.println("Connexion échouée.");
            return;
        }
        System.out.println("Connexion réussie !");

        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n========= MENU PRINCIPAL =========");
            System.out.println("1. Gestion des Employes");
            System.out.println("2. Gestion des Contrats");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1: MenuEmploye.afficherMenu(scanner); break;
                case 2: MenuContrat.afficherMenu(scanner); break;
                case 0: System.out.println("Au revoir !"); break;
                default: System.out.println("Choix invalide !");
            }
        } while (choix != 0);

        scanner.close();
    }
}