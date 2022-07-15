package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.service.ItemService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ItemService itemService;


    @PostMapping("/insertItemAll")
    public ResponseEntity<?> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception {
        return itemService.insertItemAll(multipartFiles, itemData);
    }

    @PostMapping("/revisedItem")
    public ResponseEntity<?> revisedItem(MultipartFile[] multipartFiles, String itemData, String imagePath) throws Exception {
        return itemService.revisedItem(multipartFiles, itemData, imagePath);
    }

//    @PostMapping("/removeItem/{id}")
//    public void removeItem(@PathVariable Long id) {
//         itemService.removeItem(id);
//    }
}
