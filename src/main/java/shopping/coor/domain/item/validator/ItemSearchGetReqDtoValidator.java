package shopping.coor.domain.item.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import shopping.coor.common.validator.AbstractValidator;
import shopping.coor.domain.item.dto.ItemSearchGetReqDto;
import shopping.coor.domain.item.enums.ItemCategory;

@Component
public class ItemSearchGetReqDtoValidator extends AbstractValidator<ItemSearchGetReqDto> {

    @Override
    public void validateTarget(ItemSearchGetReqDto dto, Errors errors) {
        if (StringUtils.hasText(dto.getItemLastId())) {
            try {
                Long.valueOf(dto.getItemLastId());
            } catch (Exception e) {
                errors.rejectValue("itemLastId", null, "유요하지 않는 아이디입니다.");
            }
        }

        if (StringUtils.hasText(dto.getCategory())) {
            try {
                ItemCategory.valueOf(dto.getCategory());
            } catch (Exception e) {
                errors.rejectValue("category", null, "유효하지 않는 카테고리 입니다.");
            }
        }
    }
}
