package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Image;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;

import java.util.List;

public interface ItemService {
    List<ItemRequestDto> getItem(Long lastId, int size);
    ResponseEntity<?> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception;

    List<Image> getImage();
}
