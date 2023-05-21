package net.mommymarlow.marlowclient.config;

import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import net.mommymarlow.marlowclient.Marlow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {

    private static final List<Config> configs = new ArrayList<>();

    // Used for the add animation
    public static Config lastAddedConfig;

    // Loads all configs from folder
    public static void loadConfigs() throws IOException {
        // In case of reload we don't want duplicate Configs
        configs.clear();
        // Main Folder
        @SuppressWarnings("all")
        File ROOT_DIR = new File(FabricLoader.getInstance().getGameDirectory(), Marlow.getName());
        if(!ROOT_DIR.exists()) ROOT_DIR.mkdir();

        // Configs folder
        File configFolder = new File(ROOT_DIR, "Configs");
        if(!configFolder.exists()) configFolder.mkdir();

        // Don't load anything when no configs are there
        if(configFolder.listFiles().length <= 0) {
            // Create a default config
            Config defaultConfig = new Config("default", "Default configuration");
            defaultConfig.save();
            configs.add(defaultConfig);
            Marlow.selectedConfig = defaultConfig;
            return;
        }

        for(File file : configFolder.listFiles()) {
            load(file);
        }

        // Sets the Default config active
        Marlow.selectedConfig = getDefaultConfig();
    }

    public static void addConfig(Config config) {
        configs.add(config);
        lastAddedConfig = config;
    }

    // Loads a config from a File
    public static void load(File file) throws IOException {
        BufferedReader load = new BufferedReader(new FileReader(file));
        JsonObject json = (JsonObject) JsonUtils.jsonParser.parse(load);
        load.close();

        configs.add(new Config(file.getName().replace(".json", ""), json.get("description").getAsString(), file));
    }

    // LOADS a config (the mods from it)
    public static void loadConfig(Config config) throws IOException {
        config.load();
    }

    public static Config getDefaultConfig() {
        for(Config config : configs) {
            if(config.getName().equalsIgnoreCase("default")) return config;
        }

        // When default config doesn't exist (which shouldn't be able to happen)
        // Create a default config
        Config defaultConfig = new Config("default", "Default configuration");
        try {
            defaultConfig.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        configs.add(defaultConfig);
        return defaultConfig;
    }

    public static List<Config> getConfigs() {
        return configs;
    }
}
