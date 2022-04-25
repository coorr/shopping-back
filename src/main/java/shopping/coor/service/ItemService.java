package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Image;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;
import shopping.coor.payload.request.ItemRequestOneDto;

import java.util.List;

public interface ItemService {
    List<ItemRequestDto> getItemAll(Long lastId, int size);
    ResponseEntity<?> getItemOne(Long id);
    ResponseEntity<?> removeItem(Long id);
    ResponseEntity<?> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception;
    ResponseEntity<?> revisedItem(MultipartFile[] multipartFiles, String itemData, String imagePath) throws Exception;

    List<Image> getImage();
}

