package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Image;
import shopping.coor.repository.item.dto.ItemRequestDto;

import java.util.List;

public interface ItemService {
    List<ItemRequestDto> getItemAll(Long lastId, int size, String category);
    ResponseEntity<?> getItemOne(Long id);
    void removeItem(Long id);
    ResponseEntity<?> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception;
    ResponseEntity<?> revisedItem(MultipartFile[] multipartFiles, String itemData, String imagePath) throws Exception;

    List<Image> getImage();
}

