import menus.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\n=============================================");
            System.out.println("   LOGICIEL DE GESTION RH & PAIE - ESP L2GLSI  ");
            System.out.println("=============================================");
            System.out.println("1. Gestion des Employés & Contrats (P1)");
            System.out.println("2. Gestion des Présences & Congés (P2)");
            System.out.println("3. Gestion de la Paie (Bulletins) (P3)");
            System.out.println("4. Consulter le Tableau de Bord RH (P3)");
            System.out.println("5. Quitter l'application");
            System.out.print("Entrez votre choix : ");
            
            // Sécurisation contre les saisies non numériques
            while (!scanner.hasNextInt()) {
                System.out.println("Veuillez saisir un nombre valide !");
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); // Vider le tampon

            switch (choix) {
                case 1:
                    // MenuEmploye.afficherMenu(); // Géré par P1
                    System.out.println("Module géré par P1.");
                    break;
                case 2:
                    // MenuPresence.afficherMenu(); // Géré par P2
                    System.out.println("Module géré par P2.");
                    break;
                case 3:
                    MenuBulletin.afficherMenu();
                    break;
                case 4:
                    MenuTableauBord.afficherMenu();
                    break;
                case 5:
                    System.out.println("Fermeture de l'application. Au revoir !");
                    break;
                default:
                    System.out.println("Choix incorrect, veuillez réessayer.");
            }
        } while (choix != 5);

        scanner.close();
    }
}