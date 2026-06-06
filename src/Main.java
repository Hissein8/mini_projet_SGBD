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
            System.out.println("1. Gestion des Employés");
            System.out.println("2. Gestion des Contrats");
            System.out.println("3. Gestion des Présences");
            System.out.println("4. Gestion des Congés");
            System.out.println("5. Gestion de la Paie (Bulletins)");
            System.out.println("6. Consulter le Tableau de Bord RH");
            System.out.println("7. Quitter l'application");
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
                    MenuEmploye.afficherMenu(scanner);
                    break;
                case 2:
                    // MenuPresence.afficherMenu(); // Géré par P2
                    MenuContrat.afficherMenu(scanner);
                    break;
                case 3:
                    MenuPresence.afficherMenu(scanner);
                    break;
                case 4:
                    MenuConge.afficherMenu(scanner);
                    break;
                case 5:
                    MenuBulletin.afficherMenu(scanner);
                    break;
                case 6:
                    MenuTableauBord.afficherMenu();
                    break;
                case 7:
                    System.out.println("Fermeture de l'application. Au revoir !");
                    break;
                default:
                    System.out.println("Choix incorrect, veuillez réessayer.");
            }
        } while (choix != 7);

        scanner.close();
    }
}