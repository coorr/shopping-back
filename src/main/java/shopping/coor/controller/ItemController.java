package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Item;
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
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return itemService.getItem();
    }

    @PostMapping("/insertItemAll")
    public List<Item> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception {
         return itemService.insertItemAll(multipartFiles, itemData);
    }

    @GetMapping("/request")
    public String request(@RequestParam("itemId") String itemId) {
        itemService.orderItem(itemId);
        return "ok";
    }
}
