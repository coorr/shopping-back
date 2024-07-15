package shopping.coor.domain.item.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.domain.item.dto.ItemPostCreateReqDto;
import shopping.coor.common.AbstractValidator;

@Component
public class CreateItemRequestValidator extends AbstractValidator<ItemPostCreateReqDto> {
    @Override
    public void validateTarget(ItemPostCreateReqDto target, Errors errors) {

    }
}
