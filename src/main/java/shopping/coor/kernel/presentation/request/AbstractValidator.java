package shopping.coor.kernel.presentation.request;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotNull;

public abstract class AbstractValidator<T> implements Validator {

    Class<T> tClass;

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return tClass.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object target, @NotNull Errors errors) {
        T x = tClass.cast(target);
        this.validate(x, errors);
    }

    public abstract void validateTarget(T target, Errors errors);
}
