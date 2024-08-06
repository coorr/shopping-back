package shopping.coor.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.common.exception.ValidationIllegalArgumentException;
import shopping.coor.domain.item.dto.ItemCreateReqDto;
import shopping.coor.domain.item.dto.ItemPatchUpdateReqDto;
import shopping.coor.domain.item.dto.ItemPostReqDto;
import shopping.coor.domain.item.dto.ItemUpdateReqDto;
import shopping.coor.domain.item.exception.ItemNotFoundException;
import shopping.coor.domain.item.validator.ItemPostReqDtoValidator;
import shopping.coor.domain.user.UserDetailsImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemAuthController {
    private final ItemService itemService;
    private final ItemAuthService itemAuthService;
    private final ItemPostReqDtoValidator itemPostReqDtoValidator;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable ItemIdentifier itemId, @AuthenticationPrincipal UserDetailsImpl user) {
        if (null == itemId.get()) {
            throw new ItemNotFoundException();
        }
        itemAuthService.delete(itemId.get());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Long> post(@RequestPart(value = "file", required = false) MultipartFile[] file,
                                     @Valid @RequestPart(value = "itemData") ItemPostReqDto dto,
                                     BindingResult bindingResult) {
        itemPostReqDtoValidator.validateTarget(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ValidationIllegalArgumentException(bindingResult);
        }

        Item item = itemAuthService.create(file, new ItemCreateReqDto(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(item.getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{itemId}")
    public ResponseEntity<Void> update(@PathVariable Long itemId,
                                       @RequestPart(value = "file", required = false) MultipartFile[] files,
                                       @Valid @RequestPart(value = "itemData") ItemPatchUpdateReqDto dto) {

        itemAuthService.update(itemId, files, new ItemUpdateReqDto(dto));
        return ResponseEntity.noContent().build();
    }
}
