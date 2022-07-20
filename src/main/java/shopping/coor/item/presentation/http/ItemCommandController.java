package shopping.coor.item.presentation.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.item.application.command.GetItemCommand;
import shopping.coor.item.presentation.http.request.*;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;
import shopping.coor.kernel.application.exception.ValidationIllegalArgumentException;
import shopping.coor.kernel.presentation.response.SimpleBooleanResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ItemCommandController {

    private final GetItemCommand getItemCommand;

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemGetResDto> getItem(@PathVariable Long itemId) {
        return ResponseEntity.ok().body(getItemCommand.getItem(itemId));
    }

    @GetMapping("/items")
    public ResponseEntity<List<ItemsGetResDto>> getItems(@RequestParam Long itemLastId, @RequestParam int size, @RequestParam(required = false) String category) {
        return ResponseEntity.ok().body(getItemCommand.getItems(itemLastId, size, category));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        getItemCommand.deleteItem(itemId);

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

        return ResponseEntity.status(HttpStatus.CREATED).body(getItemCommand.createItem(file, new ItemCreateReqDto(itemPostCreateReqDto)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/item/{itemId}")
    public ResponseEntity<SimpleBooleanResponse> patchItemUpdate(@PathVariable Long itemId,
                                                                 @RequestPart(value = "file", required = false) MultipartFile[] file,
                                                                 @Valid @RequestPart(value = "itemData") ItemPatchUpdateReqDto itemPatchUpdateReqDto) {
        return ResponseEntity.ok().body(new SimpleBooleanResponse(getItemCommand.updateItem(itemId, file, new ItemUpdateReqDto(itemPatchUpdateReqDto))));
    }






}
