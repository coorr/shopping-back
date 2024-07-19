package shopping.coor.common.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

@Getter
public class ValidationIllegalArgumentException extends IllegalArgumentException {
    private final Errors errors;

    public ValidationIllegalArgumentException(Errors errors) {
        this(StringUtils.EMPTY, errors);
    }

    public ValidationIllegalArgumentException(String s, Errors errors) {
        super(s);
        this.errors = errors;
    }
}
