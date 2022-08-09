package shopping.coor.common.application.exception;

public class SimpleMessageIllegalArgumentException extends IllegalArgumentException {
    public SimpleMessageIllegalArgumentException() {
        super();
    }

    public SimpleMessageIllegalArgumentException(String message) {
        super(message);
    }
}
