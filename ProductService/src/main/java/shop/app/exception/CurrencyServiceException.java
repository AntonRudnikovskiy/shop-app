package shop.app.exception;

public class CurrencyServiceException extends RuntimeException{
    public CurrencyServiceException(String message) {
        super(message);
    }
}
