package shopping.coor.service;

import org.springframework.web.multipart.MultipartFile;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;

import java.util.List;

public interface ItemService {
    List<Item> getItem();
    List<Item> insertItemAll(MultipartFile[] multipartFiles, String itemData) throws Exception;

    void orderItem(String itemId);
}
