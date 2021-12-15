package exception;

public class DiscountNotFoundException extends RuntimeException {

	public DiscountNotFoundException() {
		super();
	}

	public DiscountNotFoundException(String message) {
		super(message);
	}
	
}
