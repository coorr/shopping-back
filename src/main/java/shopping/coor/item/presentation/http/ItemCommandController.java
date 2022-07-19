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
import shopping.coor.item.presentation.http.request.ItemCreateReqDto;
import shopping.coor.item.presentation.http.request.ItemPostCreateReqDto;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;
import shopping.coor.kernel.application.exception.ValidationIllegalArgumentException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class ItemCommandController {

    private final GetItemCommand getItemCommand;

    @GetMapping("/item/{itemId}")
    public ResponseEntity<ItemGetResDto> getItem(@PathVariable Long itemId) {
        return ResponseEntity.ok().body(getItemCommand.getItemCommand(itemId));
    }

    @GetMapping("/item/items")
    public ResponseEntity<List<ItemsGetResDto>> getItems(@RequestParam Long itemLastId, @RequestParam int size, @RequestParam(required = false) String category) {
        return ResponseEntity.ok().body(getItemCommand.getItemsCommand(itemLastId, size, category));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        getItemCommand.deleteItem(itemId);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/item")
    public ResponseEntity<Long> postItemSave(@RequestPart(value = "file", required = false) MultipartFile[] file,
                             @Valid @RequestPart(value = "itemData") ItemPostCreateReqDto itemPostCreateReqDto,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationIllegalArgumentException(bindingResult);
        }
        getItemCommand.postItemSave(file, new ItemCreateReqDto(itemPostCreateReqDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();


    }




}
