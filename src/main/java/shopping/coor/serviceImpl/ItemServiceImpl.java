package shopping.coor.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.model.Item;
import shopping.coor.payload.request.ItemRequestDto;
import shopping.coor.repository.ItemRepository;
import shopping.coor.service.ItemService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Override
    public List<Item> getItem() {
        return itemRepository.getItem();
    }

    @Override
    @Transactional
    public void insertItemAll(Item item) throws  Exception{
        itemRepository.save(item);

    }

    @Override
    public void orderItem(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalStateException("예외 발생!");
        }
    }
}
