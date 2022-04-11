package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;
import shopping.coor.service.ItemService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/getItem")
    public List<Item> getItem() {
        return itemService.getItem();
    }

    @PostMapping("/insertItemAll")
    public void insertItemAll(@RequestBody Item item) {
         itemService.insertItemAll(item);
    }
}
