package api;

/**
 * Kivétel osztály
 * @since 01-03-2021 
 */

public class DbmngException extends Exception{
    private static final long serialVersionUID = 1L;

    public DbmngException(String errorMessage) {
        super(errorMessage);
    }
}
