package exceptions;

public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super(message);
    }
}
