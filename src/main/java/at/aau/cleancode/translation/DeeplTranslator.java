package at.aau.cleancode.translation;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;

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

    private String API_KEY;
    private String targetLanguage;
    private Translator translator;
    private Set<String> translatedLanguages;

    public DeeplTranslator(String targetLanguage) throws ApiKeyNotFoundException {
        this.targetLanguage = targetLanguage;
        loadApiKey();
        translator = new Translator(API_KEY);
    }

    private void loadApiKey() throws ApiKeyNotFoundException{
        String key = System.getenv("CCWEBCRAWLERAPIKEY");
        if(key == null){
            throw new ApiKeyNotFoundException();
        }
        this.API_KEY = key;
    }

    public String translate(String text) throws Exception {
        TextResult apiResponse = translator.translateText(text, null, targetLanguage);
        translatedLanguages.add(apiResponse.getDetectedSourceLanguage());
        return apiResponse.getText();
    }

    public Set<String> getTranslatedLanguages() {
        return translatedLanguages;
    }

    public static void printTargetLanguages() {
        for(String lang : supportedLanguages){
            System.out.println(lang);
        }
    }

    public static boolean checkLanguage(String selectedLanguage){
        for(String lang : supportedLanguages){
            if(selectedLanguage.equals(lang.substring(0, selectedLanguage.length()))){
                return true;
            }
        }
        return false;
    }
}
