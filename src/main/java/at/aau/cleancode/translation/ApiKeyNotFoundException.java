package at.aau.cleancode.translation;

public class ApiKeyNotFoundException extends Exception{
    public ApiKeyNotFoundException() {
        super("Translation API Key not found!");
    }
}
