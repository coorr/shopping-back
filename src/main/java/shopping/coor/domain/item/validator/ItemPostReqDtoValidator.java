package shopping.coor.domain.item.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.common.validator.AbstractValidator;
import shopping.coor.domain.item.dto.ItemPostReqDto;
import shopping.coor.domain.item.enums.ItemCategory;

@Component
public class ItemPostReqDtoValidator extends AbstractValidator<ItemPostReqDto> {
    @Override
    public void validateTarget(ItemPostReqDto target, Errors errors) {
        try {
            ItemCategory.valueOf(target.getCategory());
        } catch (Exception e) {
            errors.rejectValue("category", null, "유효하지 않는 카테고리입니다.");
        }
    }
}
