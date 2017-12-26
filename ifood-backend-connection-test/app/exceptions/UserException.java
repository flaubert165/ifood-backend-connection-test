package exceptions;

public class UserException extends Exception implements I18nException {
    public UserException() {
        super("user_exception");
    }
}
