package shopping.coor.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import shopping.coor.model.Image;
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
    public List<ItemRequestDto> getItem(@RequestParam(required = false) Long lastId, @RequestParam(required = false) int size ) {
        return itemService.getItem(lastId,size);
    }

    @PostMapping("/insertItemAll")
    public ResponseEntity<?> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception {
         return itemService.insertItemAll(multipartFiles, itemData);
    }

    @GetMapping("/getImage")
    public List<Image> getImage() {
        return itemService.getImage();
    }


}
