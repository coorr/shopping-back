package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;
import shopping.coor.model.Image;
import shopping.coor.service.ItemService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/getItem")
    public List<ItemsGetResDto> getItemAll(@RequestParam Long lastId, @RequestParam int size, @RequestParam(required = false) String category ) {
        return itemService.getItemAll(lastId,size,category);
    }


    @GetMapping("/getImage")
    public List<Image> getImage() {
        return itemService.getImage();
    }


}




















