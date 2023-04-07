package net.mcneko.vanillatranslatebot;

import com.deepl.api.Translator;
import com.deepl.api.TextResult;

public class DeepLTranslator {
    private final Translator translator;

    public DeepLTranslator(String apiKey) {
        translator = new Translator(apiKey);
    }

    public String translateText(String text, String targetLanguage) throws Exception {
        TextResult result = translator.translateText(text, null, targetLanguage);
        return result.getText();
    }
}
