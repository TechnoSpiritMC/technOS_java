package fr.techno;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadConfig {

    public static Map<String, String> getConfig() {

        Map<String, String> proprietes = new HashMap<>();

        String repertoireCourant = System.getProperty("user.dir");

        String path = "xtn.config";
        File file = new File(repertoireCourant, path);

        if (file.exists()) {
            try {
                String configFilePath = file.getAbsolutePath();
                Path pathh = Paths.get(configFilePath);

                List<String> lignes = Files.readAllLines(pathh);

                for (String ligne : lignes) {
                    String[] parts = ligne.split("=");
                    if (parts.length == 2) {
                        String cle = parts[0].trim();
                        String valeur = parts[1].trim();
                        proprietes.put(cle, valeur);
                    }
                }

                String version = proprietes.get("version");
                String nom = proprietes.get("name");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            return null;
        }

        return proprietes;
    }
}