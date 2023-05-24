package at.aau.cleancode.translation;

import java.util.Set;

public interface Translator {
    String translate(String text) throws Exception;
    Set<String> getTranslatedLanguages();
    String getTargetLanguage();
    void printTargetLanguages();
    boolean isSupportedLanguage(String selectedLanguage);
}
