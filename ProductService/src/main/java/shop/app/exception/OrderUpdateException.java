package shop.app.exception;

public class OrderUpdateException extends RuntimeException {
    public OrderUpdateException() {
    }

    public OrderUpdateException(String message) {
        super(message);
    }
}
