package shopping.coor.common.application.exception;

import org.springframework.validation.Errors;

public class ValidationIllegalArgumentException extends IllegalArgumentException {
    private Errors errors;

    public ValidationIllegalArgumentException(Errors errors) {
        super();
        this.errors = errors;
    }

    public ValidationIllegalArgumentException(String s, Errors errors) {
        super(s);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
