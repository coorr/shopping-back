package shopping.coor.common.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final String message;

    public NotFoundException(String message) {
        this(message, null);
    }

    public NotFoundException(String message, String traceMessage) {
        super(traceMessage);
        this.message = message;
    }
}
