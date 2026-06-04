package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Connexion {
    private static Map<String, String> env = chargerEnv();
    private static Map<String, String> chargerEnv() {
        Map<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("../.env"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.contains("=")) {
                    String[] parts = ligne.split("=", 2);
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lecture .env : " + e.getMessage());
        }
        return map;
    }

    public static Connection getConnexion() {
        try {
            return DriverManager.getConnection(
                env.get("DB_URL"),
                env.get("DB_USER"),
                env.get("DB_PASSWORD")
            );
        } catch (SQLException e) {
            System.out.println("Erreur de connexion : " + e.getMessage());
            return null;
        }
    }
}