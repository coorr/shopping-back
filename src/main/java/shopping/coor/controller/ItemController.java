package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Image;
import shopping.coor.repository.item.dto.ItemRequestDto;
import shopping.coor.service.ItemService;


import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    @GetMapping("/getItem")
    public List<ItemRequestDto> getItemAll(@RequestParam(required = false) Long lastId, @RequestParam(required = false) int size ) {
        return itemService.getItemAll(lastId,size);
    }

    @GetMapping("/getItemOne/{id}")
    public ResponseEntity<?> getItemOne(@PathVariable Long id) {
        return itemService.getItemOne(id);
    }

    @GetMapping("/getImage")
    public List<Image> getImage() {
        return itemService.getImage();
    }


}




















