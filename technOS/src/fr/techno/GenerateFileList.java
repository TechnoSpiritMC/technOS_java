package fr.techno;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GenerateFileList {
    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "ZAGdCFv4cqkyFD8GBjGseyonrV7cNHlQ".getBytes(); // Clé secrète pour le chiffrement

    public static void main(String[] args) {
        String filePath = "files.yaml";

        try {
            Map<String, Map<String, String>> yamlData = loadDataFromFile(filePath);

            printData(yamlData);

            addFile("CheckFile", ("src" + File.separator + "fr"+ File.separator + "techno" + File.separator + "CheckFile.java"), yamlData);
            addFile("Init", ("src" + File.separator + "fr"+ File.separator + "techno" + File.separator + "Init.java"), yamlData);
            addFile("LoadConfig", ("src" + File.separator + "fr"+ File.separator + "techno" + File.separator + "LoadConfig.java"), yamlData);

            addFile("Main", ("src" + File.separator + "fr"+ File.separator + "techno" + File.separator + "Main.java"), yamlData);
            addFile("SaveFiles", ("src" + File.separator + "fr"+ File.separator + "techno" + File.separator + "SaveFiles.java"), yamlData);
            addFile("Utils", ("src" + File.separator + "fr"+ File.separator + "techno" + File.separator + "Utils.java"), yamlData);

            saveDataToFile(filePath, yamlData);
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

    private static void saveDataToFile(String filePath, Map<String, Map<String, String>> yamlData) throws Exception {
        PrintWriter writer = new PrintWriter(filePath);
        for (Map.Entry<String, Map<String, String>> entry : yamlData.entrySet()) {
            writer.println(entry.getKey() + ":");
            for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                writer.println("   " + innerEntry.getKey() + ": " + encrypt(innerEntry.getValue()));
            }
        }
        writer.close();
    }

    private static String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedValue = cipher.doFinal(value.getBytes());
        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    private static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedValue);
    }

    private static void printData(Map<String, Map<String, String>> yamlData) {
        for (Map.Entry<String, Map<String, String>> entry : yamlData.entrySet()) {
            System.out.println("Clé: " + entry.getKey());
            for (Map.Entry<String, String> innerEntry : entry.getValue().entrySet()) {
                System.out.println("   " + innerEntry.getKey() + ": " + innerEntry.getValue());
            }
        }
    }

    public static void addFile(String name, String path, Map<String, Map<String, String>> yamlData) {
        Map<String, String> fileData = new HashMap<>();
        fileData.put("path", path);
        yamlData.put(name, fileData);
    }

    public static void delFile(String name, Map<String, Map<String, String>> yamlData) {
        yamlData.remove(name);
    }
}