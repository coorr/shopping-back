package shopping.coor.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.model.Item;
import shopping.coor.repository.ItemRepository;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품_추가() throws Exception {
//        Item item = new Item();
//        item.setTitle("아아");
//
//        Item itemId = itemRepository.save(item);
//
//        assertEquals(item.getTitle(), itemId.getTitle());
        System.out.println("12");


    }
}