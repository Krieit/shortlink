package assignment.shortlink.exception;

public class UrlNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UrlNotFoundException(String message) {
        super(message);
    }
}