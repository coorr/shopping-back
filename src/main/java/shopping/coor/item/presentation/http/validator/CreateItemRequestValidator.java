package shopping.coor.item.presentation.http.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.item.presentation.http.request.ItemPostCreateReqDto;
import shopping.coor.common.presentation.request.AbstractValidator;

@Component
public class CreateItemRequestValidator extends AbstractValidator<ItemPostCreateReqDto> {
    @Override
    public void validateTarget(ItemPostCreateReqDto target, Errors errors) {

    }
}
