import connexion.Connexion;
import java.sql.Connection;
import menus.MenuConge;
import menus.MenuPresence;

public class Main {
    public static void main(String[] args) {
    // System.out.println(System.getProperty("user.dir"));
        Connection conn = Connexion.getConnexion();
        if (conn != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Connexion échouée.");
        }

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int choix = -1;

        while (choix != 0) {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1. Gestion des Présences");
            System.out.println("2. Gestion des Congés");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            choix = Integer.parseInt(scanner.nextLine());

            switch (choix) {
                case 1: new MenuPresence().afficherMenu(); break;
                case 2: new MenuConge().afficherMenu(); break;
                case 0: System.out.println("Au revoir !"); break;
                default: System.out.println("Choix invalide.");
            }
       }
    }
}