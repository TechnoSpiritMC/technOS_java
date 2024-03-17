package fr.techno;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckFile {

    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "ZAGdCFv4cqkyFD8GBjGseyonrV7cNHlQ".getBytes(); // Clé secrète pour le chiffrement

    public static void main(String[] args) {
        String filePath = "files.yaml";

        try {
            Map<String, Map<String, String>> yamlData = loadDataFromFile(filePath);

            //#################################################

            System.out.println(getFiles(yamlData));

            //#################################################

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Map<String, String>> loadDataFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        Map<String, Map<String, String>> yamlData = new HashMap<>();

        String currentKey = null;
        Map<String, String> currentEntry = null;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
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
                    String value = decrypt(matcher.group(2));
                    currentEntry.put(key, value);
                }
            }
        }

        scanner.close();
        return yamlData;
    }

    private static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedValue);
    }

    private static Map<String, Boolean> getFiles(Map<String, Map<String, String>> yamlData) {

        Map<String, Boolean> files = new HashMap<>();

        for (Map.Entry<String, Map<String, String>> entry : yamlData.entrySet()) {

            for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                System.out.println("   " + innerEntry.getKey() + ": " + innerEntry.getValue());

                if (innerEntry.getKey().equals("path")) {
                    File file = new File(innerEntry.getValue());

                    if (file.exists()) {
                        files.put(entry.getKey(), true);
                    } else {
                        files.put(entry.getKey(), false);
                    }
                }



            }

            System.out.println("Clé: " + entry.getKey());
            for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                System.out.println("   " + innerEntry.getKey() + ": " + innerEntry.getValue());

            }
        }
        return files;
    }
}