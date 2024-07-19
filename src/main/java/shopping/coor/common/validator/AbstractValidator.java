package shopping.coor.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public abstract class AbstractValidator<T> implements Validator {

    Class<T> tClass; // CreateUserRequest

    @Override
    public boolean supports(Class<?> clazz) {
        return tClass.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        T x = tClass.cast(target);
        this.validateTarget(x, errors);
    }

    public abstract void validateTarget(T target, Errors errors);
}
