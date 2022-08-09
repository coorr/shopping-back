package shopping.coor.basket.presentation.http.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.common.presentation.request.AbstractValidator;

@Component
public class BasketPostReqDtoValidator extends AbstractValidator<BasketPostReqDto> {
    @Override
    public void validateTarget(BasketPostReqDto target, Errors errors) {

    }
}
