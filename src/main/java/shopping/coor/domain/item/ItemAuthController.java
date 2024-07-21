package shopping.coor.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.common.container.SimpleBooleanResponse;
import shopping.coor.common.exception.ValidationIllegalArgumentException;
import shopping.coor.domain.item.dto.ItemCreateReqDto;
import shopping.coor.domain.item.dto.ItemPatchUpdateReqDto;
import shopping.coor.domain.item.dto.ItemPostCreateReqDto;
import shopping.coor.domain.item.dto.ItemUpdateReqDto;
import shopping.coor.domain.item.exception.ItemNotFoundException;
import shopping.coor.domain.user.UserDetailsImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemAuthController {
    private final ItemService itemService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable ItemIdentifier itemId, @AuthenticationPrincipal UserDetailsImpl user) {
        if (null == itemId.get()) {
            throw new ItemNotFoundException();
        }
        itemService.delete(itemId.get());

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/item")
    public ResponseEntity<Long> postItemCreate(@RequestPart(value = "file", required = false) MultipartFile[] file,
                                               @Valid @RequestPart(value = "itemData") ItemPostCreateReqDto itemPostCreateReqDto,
                                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationIllegalArgumentException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createItem(file, new ItemCreateReqDto(itemPostCreateReqDto)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/item/{itemId}")
    public ResponseEntity<SimpleBooleanResponse> patchItemUpdate(@PathVariable Long itemId,
                                                                 @RequestPart(value = "file", required = false) MultipartFile[] file,
                                                                 @Valid @RequestPart(value = "itemData") ItemPatchUpdateReqDto itemPatchUpdateReqDto) {
        return ResponseEntity.ok().body(new SimpleBooleanResponse(itemService.updateItem(itemId, file, new ItemUpdateReqDto(itemPatchUpdateReqDto))));
    }
}
