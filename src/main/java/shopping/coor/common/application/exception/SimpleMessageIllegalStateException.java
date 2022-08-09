package shopping.coor.common.application.exception;

public class SimpleMessageIllegalStateException extends IllegalStateException {
    public SimpleMessageIllegalStateException() {
        super();
    }

    public SimpleMessageIllegalStateException(String message) {
        super(message);
    }
}
