package shop.app.exception;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException() {
    }

    public ProductNotAvailableException(String message) {
        super(message);
    }

    public ProductNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
