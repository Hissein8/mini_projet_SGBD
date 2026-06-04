import connexion.Connexion;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
    // System.out.println(System.getProperty("user.dir"));
        Connection conn = Connexion.getConnexion();
        if (conn != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Connexion échouée.");
        }
    }
}