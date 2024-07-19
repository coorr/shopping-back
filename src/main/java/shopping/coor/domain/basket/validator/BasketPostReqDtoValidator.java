package shopping.coor.domain.basket.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.domain.basket.dto.BasketPostReqDto;
import shopping.coor.common.validator.AbstractValidator;

@Component
public class BasketPostReqDtoValidator extends AbstractValidator<BasketPostReqDto> {
    @Override
    public void validateTarget(BasketPostReqDto target, Errors errors) {

    }
}
