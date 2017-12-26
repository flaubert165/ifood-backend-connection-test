package exceptions;

public class AuthenticationException extends Exception implements I18nException {
    public AuthenticationException() {
        super("authentication_exception");
    }
}
