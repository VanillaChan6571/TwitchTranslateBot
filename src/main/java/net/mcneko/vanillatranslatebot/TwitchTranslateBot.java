package net.mcneko.vanillatranslatebot;

import com.deepl.api.LanguageCode;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.delay.StaticDelay;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;

import java.util.*;

public class TwitchTranslateBot extends ListenerAdapter {
    private final DeepLTranslator deepLTranslator;
    private final PircBotX bot;
    private final String targetLanguage;
    private final String targetLanguageSecondary;
    private final UserPreferences userPreferences;
    private final Set<String> validLanguageCodes;

    public TwitchTranslateBot(String twitchUsername, String twitchOauthKey, String deeplApiKey, String channel, String targetLanguage, String targetLanguageSecondary) {
        this.deepLTranslator = new DeepLTranslator(deeplApiKey);
        this.targetLanguage = targetLanguage;
        this.targetLanguageSecondary = targetLanguageSecondary;

        Configuration configuration = new Configuration.Builder()
                .setName(twitchUsername)
                .setServerPassword(twitchOauthKey)
                .addServer("irc.chat.twitch.tv", 6667)
                .addAutoJoinChannel("#" + channel)
                .addListener(this)
                .setAutoReconnect(true)
                .setAutoReconnectAttempts(15)
                .setAutoReconnectDelay(new StaticDelay(5000))
                .setCapEnabled(true)
                .buildConfiguration();

        bot = new PircBotX(configuration);
        userPreferences = new UserPreferences();
        validLanguageCodes = new HashSet<>(Arrays.asList("EN", "ES")); // Add the valid language codes here
    }

    @Override
    public void onMessage(MessageEvent event) throws InterruptedException {
        String message = event.getMessage();
        String user = Objects.requireNonNull(event.getUser()).getNick();

        if (message.startsWith("!translate")) {
            String[] parts = message.split(" ");
            if (parts.length < 2) {
                event.getChannel().send().message(user + " (English) Usage: !translate <key> the current keys available are: " + String.join(", ", validLanguageCodes) + " (Español) ¡Error! Uso correcto: !translate <key> las teclas actuales disponibles son:" + String.join(", ", validLanguageCodes));
            } else {
                String languageCode = parts[1].toUpperCase();
                if (validLanguageCodes.contains(languageCode)) {
                    userPreferences.setUserLanguage(user, languageCode);
                    event.getChannel().send().message(user + "(English) You are now translating your messages to:" + languageCode + "(Español) Ahora está traduciendo sus mensajes a: :" + languageCode);
                    wait(2000);
                    event.getChannel().send().message("Saving your preference so you don't need to run the command again... | Guarda tus preferencias para no tener que volver a ejecutar el comando... ");
                } else {
                    event.getChannel().send().message(user + " (English) Error! Correct Usage: !translate <key> the current keys available are: " + String.join(", ", validLanguageCodes) + " (Español) ¡Error! Uso correcto: !translate <key> las teclas actuales disponibles son: " + String.join(", ", validLanguageCodes));
                }
            }
            return;
        }

        if (!message.startsWith("!")) {
            try {
                String targetLanguage = userPreferences.getUserLanguage(user);
                if (targetLanguage != null) {
                    String translatedMessage = deepLTranslator.translateText(message, targetLanguage);
                    event.getChannel().send().message(user + ": " + translatedMessage);
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.getChannel().send().message("Couwd nyot t-twanswate the message! C-Check Consowe fow ewwows! | ¡Nyo s-se pudo twaduciw ew mensaje! ¡Compwuebe wa consowa pawa vew si hay ewwowes!");
            }
        }
    }


    public void startBot() throws Exception {
        bot.startBot();
    }

    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader("config.properties");
        Properties properties = configLoader.loadConfig();
        properties = configLoader.loadConfig();

        String twitchUsername = properties.getProperty("twitch_username");
        String twitchOauthKey = properties.getProperty("twitch_oauth_key");
        String deeplApiKey = properties.getProperty("deepl_api_key");
        String channel = properties.getProperty("channel");
        String targetLanguage = properties.getProperty("target_language", LanguageCode.EnglishAmerican);
        String targetLanguageSecondary = properties.getProperty("target_language_secondary", LanguageCode.Spanish);

        TwitchTranslateBot bot = new TwitchTranslateBot(twitchUsername, twitchOauthKey, deeplApiKey, channel, targetLanguage, targetLanguageSecondary);

        boolean started = false;
        try {
            bot.startBot();
            started = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("System Started: " + (started ? "OK" : "FAILED"));
        }
    }
}