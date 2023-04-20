package at.aau.cleancode.test;

import at.aau.cleancode.translation.DeeplTranslator;
import at.aau.cleancode.translation.DeeplTranslatorException;
import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeeplTranslatorTest {

    Translator translator = mock(Translator.class);

    @BeforeEach
    public void resetMocks(){
        reset(translator);
    }

    @Test
    public void initTranslatorTest() throws DeeplTranslatorException {
        assertNotNull(new DeeplTranslator("DE", "apikey"));
    }

    @Test
    public void createTranslatorWithNoApiKey(){
        assertThrows(DeeplTranslatorException.class, () -> {new DeeplTranslator("", null);});
    }

    @Test
    public void createTranslatorWithInvalidTargetLanguage(){
        assertThrows(DeeplTranslatorException.class, () -> {new DeeplTranslator("", "apikey");});
    }

    @Test
    public void testTranslatedLanguages() throws Exception {
        TextResult result = mock(TextResult.class);
        when(result.getDetectedSourceLanguage()).thenReturn("DE");
        when(translator.translateText((String) any(), (String) any(),any())).thenReturn(result);
        DeeplTranslator deeplTranslator = new DeeplTranslator(translator);
        deeplTranslator.translate("Hello");

        when(result.getDetectedSourceLanguage()).thenReturn("EN-GB");
        when(translator.translateText((String) any(), (String) any(),any())).thenReturn(result);
        deeplTranslator.translate("Hello2you");

        verify(translator, times(2)).translateText((String) any(), (String) any(),any());
        assertEquals(2, deeplTranslator.getTranslatedLanguages().size());
        assertTrue(deeplTranslator.getTranslatedLanguages().contains("German"));
        assertTrue(deeplTranslator.getTranslatedLanguages().contains("English (British)"));
    }

    @Test
    public void testSupportedLanguages(){
        for(String lang : DeeplTranslator.supportedLanguages){
            String shortcode = lang.split(" - ")[0];
            assertTrue(DeeplTranslator.isSupportedLanguage(shortcode));
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"de", "ef", "Deutsch", "Spaghetti", "1"})
    public void testUnsupportedLanguage(String lang){
        assertFalse(DeeplTranslator.isSupportedLanguage(lang));
    }
}
