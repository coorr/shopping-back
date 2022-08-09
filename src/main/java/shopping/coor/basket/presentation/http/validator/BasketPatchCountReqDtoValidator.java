package shopping.coor.basket.presentation.http.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.basket.domain.enums.BasketOrder;
import shopping.coor.basket.presentation.http.request.BasketPatchCountReqDto;
import shopping.coor.common.presentation.request.AbstractValidator;

@Component
public class BasketPatchCountReqDtoValidator extends AbstractValidator<BasketPatchCountReqDto> {
    @Override
    public void validateTarget(BasketPatchCountReqDto target, Errors errors) {
        try {
            BasketOrder.valueOf(target.getOrder());
        } catch (Exception e) {
            errors.rejectValue("order", "필수 값을 채워주세요.");
        }
    }
}
