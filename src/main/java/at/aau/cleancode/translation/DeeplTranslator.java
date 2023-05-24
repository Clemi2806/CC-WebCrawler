package at.aau.cleancode.translation;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;

import java.util.HashSet;
import java.util.Set;

public class DeeplTranslator implements at.aau.cleancode.translation.Translator {
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

    public DeeplTranslator(){
        super();
        translatedLanguages = new HashSet<>();
    }

    @Override
    public String translate(String text) throws Exception {
        TextResult apiResponse = translator.translateText(text, null, targetLanguage);
        translatedLanguages.add(apiResponse.getDetectedSourceLanguage());
        return apiResponse.getText();
    }

    @Override
    public Set<String> getTranslatedLanguages() {
        Set<String> languages = new HashSet<>();
        for (String language : translatedLanguages) {
            for (String supportedLanguage : supportedLanguages) {
                String shortcode = supportedLanguage.split(" - ")[0];
                String languageName = supportedLanguage.split(" - ")[1];
                if (shortcode.equalsIgnoreCase(language)) {
                    languages.add(languageName);
                }
            }

        }
        return languages;
    }

    @Override
    public void printTargetLanguages() {
        for (String lang : supportedLanguages) {
            System.out.println(lang);
        }
    }

    @Override
    public boolean isSupportedLanguage(String selectedLanguage) {
        for (String lang : supportedLanguages) {
            if (selectedLanguage.equals(lang.split(" - ")[0])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setTargetLanguage(String targetLanguage) throws TranslatorException{
        if(!isSupportedLanguage(targetLanguage)) throw new TranslatorException("Invalid target language!");
        this.targetLanguage = targetLanguage;
    }

    @Override
    public void connect(String apikey) throws TranslatorException {
        if(apikey == null) throw new TranslatorException("APIKey was null");
        this.translator = new Translator(apikey);
    }

    @Override
    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }
}
