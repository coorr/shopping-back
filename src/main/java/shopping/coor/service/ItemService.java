package shopping.coor.service;

import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;

import java.util.List;

public interface ItemService {
    List<Item> getItem();
    void insertItemAll(Item item) throws Exception;

    void orderItem(String itemId);
}
