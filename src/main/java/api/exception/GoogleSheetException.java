package api.exception;

public class GoogleSheetException extends Exception {
    
    
    private static final long serialVersionUID = -599516014284159325L;

    public GoogleSheetException() {
        super();
    }

    public GoogleSheetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GoogleSheetException(String message, Throwable cause) {
        super(message, cause);
    }

    public GoogleSheetException(String message) {
        super(message);
    }

    public GoogleSheetException(Throwable cause) {
        super(cause);
    }
    
}