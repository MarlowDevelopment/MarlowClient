package net.mommymarlow.marlowclient.utils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.mommymarlow.marlowclient.Marlow;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AutoUpdate {

    private static final String VERSION_FILE_URL = "https://raw.githubusercontent.com/MarlowDevelopment/MarlowClient/master/version";
    private static final String MOD_UPDATE_URL = "https://github.com/MarlowDevelopment/MarlowClient/releases/download/latest/MarlowClient-latest.jar";

    private static final String FEATHER_PATH = System.getProperty("user.home")+"/AppData/Roaming/.feather/user-mods/1.19.3-fabric/MarlowClient-\"+getCurrentVersion()+\".jar";

    private static final String MOD_FILE_PATH = FabricLoader.getInstance().getGameDir() + "/mods/MarlowClient-"+getCurrentVersion()+".jar";

    /*
    public static String MarlowVersion(){
        File latestFile = new File(FabricLoader.getInstance().getGameDir() + "/mods/MarlowClient-latest.jar");
        File versionFile = new File(FabricLoader.getInstance().getGameDir() + "/mods/MarlowClient-"+Marlow.VERSION+".jar");
        if (latestFile.exists() && !versionFile.exists()){
            return "MarlowClient-latest.jar";
        }else if (versionFile.exists() && !latestFile.exists()){
            return "MarlowClient-"+Marlow.VERSION+".jar";
        }
        return Marlow.VERSION;
    }

     */


    private static String getFeatherOrNormal(){

        return null;
    }

    private static String getCurrentVersion() {
        // Retrieve the current mod version from a local file or another source
        // Return the version as a String
       return Marlow.VERSION;
    }

    public static String getLatestVersion() {
        try {// Fetch the latest mod version from a remote version file
            URL url = new URL(VERSION_FILE_URL);
        /*
        try (BufferedInputStream inputStream = new BufferedInputStream(url.openStream())) {
            return new String(inputStream.readAllBytes());
        }
         */
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();

            String latestVserion = content.toString();
            return latestVserion;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateMod() throws IOException {
        // Download the updated mod JAR file from a remote source
        URL url = new URL(MOD_UPDATE_URL);
        try (BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(MOD_FILE_PATH)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
        URL url2 = new URL(MOD_UPDATE_URL);
        try (BufferedInputStream inputStream = new BufferedInputStream(url2.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(FEATHER_PATH)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        }
    }

    public static void doUpdate() {
        try {
            String currentVersion = getCurrentVersion();
            String latestVersion = getLatestVersion();

            if (!currentVersion.equals(latestVersion)) {
                System.out.println("An update is available!");

                // Perform any necessary actions before updating (e.g., data migration)

                updateMod();

                // Perform any necessary actions after updating (e.g., configuration update)

                System.out.println("Mod updated to version " + latestVersion);
            } else {
                System.out.println("Mod is up to date.");
            }
        } catch (IOException e) {
            System.err.println("Error checking for updates: " + e.getMessage());
        }
    }
}
