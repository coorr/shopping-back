package shopping.coor.item.presentation.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shopping.coor.item.application.command.GetItemCommand;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;

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


}
