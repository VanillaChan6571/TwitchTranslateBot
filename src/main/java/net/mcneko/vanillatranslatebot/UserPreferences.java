package net.mcneko.vanillatranslatebot;

import java.io.*;
import java.util.HashMap;

public class UserPreferences {
    private HashMap<String, String> userLanguages;
    private final String fileName = "user_preferences.ser";

    @SuppressWarnings("unchecked")
    public UserPreferences() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            userLanguages = (HashMap<String, String>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            userLanguages = new HashMap<>();
        }
    }

    public void setUserLanguage(String user, String language) {
        userLanguages.put(user, language);
        savePreferences();
    }

    public String getUserLanguage(String user) {
        return userLanguages.getOrDefault(user, null);
    }

    private void savePreferences() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userLanguages);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
