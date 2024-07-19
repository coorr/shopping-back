package shopping.coor.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class SimpleMessageIllegalStateException extends IllegalStateException {
    private final String message;
    private final String field;
    private final Object[] parameters;

    public SimpleMessageIllegalStateException(String message) {
        this(message, null);
    }

    public SimpleMessageIllegalStateException(String message, String field) {
        this(message, field, null, StringUtils.EMPTY);
    }

    public SimpleMessageIllegalStateException(String message, String field, Object[] parameters, String traceMessage) {
        super(traceMessage);
        this.message = message;
        this.field = field;
        this.parameters = parameters;
    }
}
