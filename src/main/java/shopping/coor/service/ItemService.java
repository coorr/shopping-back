package shopping.coor.service;

import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;

import java.util.List;

public interface ItemService {
    public List<Item> getItem();
    public void insertItemAll(Item item);
}
