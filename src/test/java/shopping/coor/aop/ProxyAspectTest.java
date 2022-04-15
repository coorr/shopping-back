package shopping.coor.aop;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.coor.controller.ItemController;
import shopping.coor.model.Item;

import java.util.List;

@SpringBootTest
@Slf4j
class ProxyAspectTest {

    @Autowired
    ItemController itemController;



    @Test
    public void aopTest() throws Exception {
        log.info("itemService = {}", itemController.getClass());
        List<Item> item = itemController.getItem();
        System.out.println("item = " + item);
    }
}