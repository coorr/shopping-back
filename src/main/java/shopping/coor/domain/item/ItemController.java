package shopping.coor.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.common.exception.ValidationIllegalArgumentException;
import shopping.coor.domain.item.dto.ItemGetResDto;
import shopping.coor.domain.item.dto.ItemSearchGetReqDto;
import shopping.coor.domain.item.dto.ItemsGetResDto;
import shopping.coor.domain.item.exception.ItemNotFoundException;
import shopping.coor.domain.item.validator.ItemSearchGetReqDtoValidator;

import java.util.List;

@RestController
@RequestMapping(value = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemSearchGetReqDtoValidator itemSearchGetReqDtoValidator;

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemGetResDto> getItem(@PathVariable ItemIdentifier itemId) {
        if (null == itemId.get()) {
            throw new ItemNotFoundException();
        }
        ItemGetResDto resDto = new ItemGetResDto(itemService.getItem(itemId.get()));
        return ResponseEntity.ok().body(resDto);
    }

    @GetMapping
    public ResponseEntity<List<ItemsGetResDto>> getItems(ItemSearchGetReqDto dto, BindingResult bindingResult) {
        itemSearchGetReqDtoValidator.validateTarget(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationIllegalArgumentException(bindingResult);
        }
        return ResponseEntity.ok().body(itemService.getItems(dto));
    }
}
