package api;

public class DbmngException extends Exception{
    private static final long serialVersionUID = 1L;

    public DbmngException(String errorMessage) {
        super(errorMessage);
    }
}
