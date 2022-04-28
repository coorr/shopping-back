package shopping.coor.service;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.model.Item;
import shopping.coor.repository.ItemRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @Test
    public void 상품_save() throws Exception {
        Item item = new Item();
        item.setTitle("시어서커 크롭 자켓 (다크네이비)");
        item.setPrice(20000);
        item.setDiscountPrice(18000);
        item.setCategory("outwear");
        item.setInfo("- 크롭한 기장감의 시원한 시어서커 원사를 사용한 오버핏 실루엣 자켓" +
                    "- 앞,뒤 이어지는 부분 자연스러운 턱 디테일" +
                    "- 볼륨감 있는 소매 라인" +
                    "- 전면부 히든 여밈 디테일");
        item.setMaterial("COTTON 78% POLYESTER 20% POLYURETHANE 2%");
        item.setSize("S 총장 62.5cm 어깨 53cm 가슴 60.5cm 소매 58.4cm" +
                    "M 총장 64cm 어깨 54.5cm 가슴 63cm 소매 59.7cm" +
                    "L 총장 65.5cm 어깨 56cm 가슴 65.5cm 소매 61cm");
        Item itemId = itemRepository.save(item);

        assertEquals(item, itemId);
    }

    @Test
    public void 상품_추가_파일은없음() throws Exception {
        JSONObject itemData = new JSONObject();
        itemData.put("title", "시어서커 블랙 자켓(다크네이비)");
        itemData.put("price", 20000);
        ResponseEntity<?> responseEntity = itemService.insertItemAll(null, itemData.toString());

        assertEquals(responseEntity, null);
    }

}