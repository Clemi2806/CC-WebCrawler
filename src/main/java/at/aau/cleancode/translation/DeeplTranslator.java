package at.aau.cleancode.translation;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DeeplTranslator {
    public static final String[] supportedLanguages = {
            "BG - Bulgarian",
            "CS - Czech",
            "DA - Danish",
            "DE - German",
            "EL - Greek",
            "EN-GB - English (British)",
            "EN-US - English (American)",
            "ES - Spanish",
            "ET - Estonian",
            "FI - Finnish",
            "FR - French",
            "HU - Hungarian",
            "ID - Indonesian",
            "IT - Italian",
            "JA - Japanese",
            "KO - Korean",
            "LT - Lithuanian",
            "LV - Latvian",
            "NB - Norwegian (Bokmål)",
            "NL - Dutch",
            "PL - Polish",
            "PT-BR - Portuguese (Brazilian)",
            "PT-PT - Portuguese (all Portuguese varieties excluding Brazilian Portuguese)",
            "RO - Romanian",
            "RU - Russian",
            "SK - Slovak",
            "SL - Slovenian",
            "SV - Swedish",
            "TR - Turkish",
            "UK - Ukrainian",
            "ZH - Chinese (simplified)"
    };

    private String targetLanguage;
    private Translator translator;
    private Set<String> translatedLanguages;

    public DeeplTranslator(String targetLanguage, String apikey) throws DeeplTranslatorException {
        if(apikey == null) throw new DeeplTranslatorException("API Key was null");
        if(!isSupportedLanguage(targetLanguage)) throw new DeeplTranslatorException("Invalid target language!");
        this.targetLanguage = targetLanguage;
        translator = new Translator(apikey);
        translatedLanguages = new HashSet<>();
    }

    public DeeplTranslator(Translator translator, String targetLanguage) {
        this.translator = translator;
        translatedLanguages = new HashSet<>();
        this.targetLanguage = targetLanguage;
    }

    public String translate(String text) throws Exception {
        TextResult apiResponse = translator.translateText(text, null, targetLanguage);
        translatedLanguages.add(apiResponse.getDetectedSourceLanguage());
        return apiResponse.getText();
    }

    public Set<String> getTranslatedLanguages() {
        Set<String> languages = new HashSet<>();
        for(String language : translatedLanguages){
            for(String supportedLanguage : supportedLanguages){
                String shortcode = supportedLanguage.split(" - ")[0];
                String languageName = supportedLanguage.split(" - ")[1];
                if(shortcode.equalsIgnoreCase(language)){
                    languages.add(languageName);
                }
            }

        }
        return languages;
    }

    public static void printTargetLanguages() {
        for(String lang : supportedLanguages){
            System.out.println(lang);
        }
    }

    public static boolean isSupportedLanguage(String selectedLanguage){
        for(String lang : supportedLanguages){
            if(selectedLanguage.equals(lang.split(" - ")[0])){
                return true;
            }
        }
        return false;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }
}
