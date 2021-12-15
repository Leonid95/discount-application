package exception;

public class InvalidDiscountException extends RuntimeException {

	public InvalidDiscountException() {
		super();
	}

	public InvalidDiscountException(String message) {
		super(message);
	}

}
