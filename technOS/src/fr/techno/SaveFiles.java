package fr.techno;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SaveFiles {
    public static void main(String[] args) {
        String filePath = "technOS/xtn.yaml";
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            Map<String, Map<String, String>> yamlData = new HashMap<>();

            String currentKey = null;
            Map<String, String> currentEntry = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Ignore les lignes vides ou les commentaires
                }

                if (line.endsWith(":")) {
                    currentKey = line.substring(0, line.length() - 1).trim();
                    currentEntry = new HashMap<>();
                    yamlData.put(currentKey, currentEntry);
                } else {
                    Pattern pattern = Pattern.compile("([^:]+): (.+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.matches() && currentEntry != null) {
                        String key = matcher.group(1);
                        String value = matcher.group(2);
                        currentEntry.put(key, value);
                    }
                }
            }

            // Affichage des données lues
            for (Map.Entry<String, Map<String, String>> entry : yamlData.entrySet()) {
                System.out.println("Clé: " + entry.getKey());
                for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                    System.out.println("   " + innerEntry.getKey() + ": " + innerEntry.getValue());
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
