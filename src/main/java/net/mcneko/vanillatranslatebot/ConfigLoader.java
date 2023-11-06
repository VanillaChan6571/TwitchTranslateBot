package net.mcneko.vanillatranslatebot;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigLoader {
    private final String configPath;

    public ConfigLoader(String configPath) {
        this.configPath = configPath;
    }

    public Properties loadConfig() {
        Properties properties = new Properties();
        try {
            if (!Files.exists(Paths.get(configPath))) {
                createDefaultConfig();
            }
            InputStream inputStream = new FileInputStream(configPath);
            properties.load(inputStream);
        } catch (IOException e) {
            System.err.println("Failed to load config file: " + configPath);
            e.printStackTrace();
        }
        return properties;
    }

    public void saveConfig(Properties properties) {
        try {
            FileOutputStream outputStream = new FileOutputStream(configPath);
            properties.store(outputStream, null);
        } catch (IOException e) {
            System.err.println("Failed to save config file: " + configPath);
            e.printStackTrace();
        }
    }

    /**
     * Creates a default configuration file with default values for the properties.
     */
    private void createDefaultConfig() {
        // Create a new instance of Properties
        Properties defaultProperties = new Properties();

        // Set the default values for the properties
        defaultProperties.setProperty("twitch_username", "your_twitch_username");
        defaultProperties.setProperty("twitch_oauth_key", "your_twitch_oauth_key");
        defaultProperties.setProperty("deepl_api_key", "your_deepl_api_key");
        defaultProperties.setProperty("channel", "your_target_channel");
        defaultProperties.setProperty("target_language", "EN");

        // Add a comment to the properties
        defaultProperties.setProperty("#twitch_oauth_key_comment", "To get your oauth key head to https://twitchapps.com/tmi/");

        // Save the configuration file with the default properties
        saveConfig(defaultProperties);

        // Print a message indicating that the default config file has been created
        System.out.println("Created default config file: " + configPath);
    }
}