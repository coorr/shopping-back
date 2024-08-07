package shopping.coor.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.coor.domain.basket.Basket;
import shopping.coor.domain.item.Item;
import shopping.coor.domain.item.enums.ItemCategory;
import shopping.coor.domain.order.Order;
import shopping.coor.domain.user.User;
import shopping.coor.domain.basket.BasketRepository;
import shopping.coor.domain.item.ItemRepository;
import shopping.coor.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BasketRepositoryTest {

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    List<Basket> basketList;

    @BeforeEach
    void before() {
        userRepository.save(user());
        itemRepository.save(item());
        basketList = basketRepository.saveAll(baskets());
    }


    @Test
    public void 장바구니_조회() throws Exception {
        // given
//        List<Basket> basketList = basketRepository.saveAll(baskets());

        // when
        List<Basket> result = basketRepository.findAllByUserId(user());

        // then
        assertEquals(basketList.size(), result.size());
        assertEquals(basketList.get(0).getCount(), result.get(0).getCount());
        assertEquals(basketList.get(0).getTotal(), result.get(0).getTotal());
    }

//    @Test
//    public void 장바구니_아이템_수량_올리기() throws Exception {
//        // given
//        List<Basket> basketList = basketRepository.saveAll(baskets());
//
//        // when
//        basketRepository.updateCountPlusById(basketList.get(0).getId(), 50000);
//        Basket result = basketRepository.getById(basketList.get(0).getId());
//        // then
//        assertEquals(basketList.get(0).getItemTotal()+50000, result.getItemTotal());
//        assertEquals(basketList.get(0).getItemCount()+1 , result.getItemCount());
//    }

//    @Test
//    public void 장바구니_아이템_수량_내리기() throws Exception {
//        // given
//        List<Basket> basketList = basketRepository.saveAll(baskets());
//
//        // when
//        basketRepository.updateCountDownById(basketList.get(0).getId(), 30000);
//        Basket result = basketRepository.getById(basketList.get(0).getId());
//        // then
//        assertEquals(basketList.get(0).getItemTotal()-30000, result.getItemTotal());
//        assertEquals(basketList.get(0).getItemCount()-1 , result.getItemCount());
//    }

//    @Test
//    public void 장바구니_유저_아이템_전체삭제() throws Exception {
//        // given
//        List<Basket> basketList = basketRepository.saveAll(baskets());
//
//        // when
//        basketRepository.deleteBasketByUserId(basketList.get(0).getUser());
//        List<Basket> result = basketRepository.findAllByUserId(basketList.get(0).getUser());
//        // then
//        assertEquals(result.size(), 0);
//    }

    private User user() {
        List<Order> orders = new ArrayList<>();
        return User.builder()
                .id(1L)
                .name("kim1")
                .email("W@naver.com")
                .password("123123")
                .orders(orders)
                .build();
    }
    private Item item() {
        return Item.builder()
                .id(1L)
                .price(20000)
                .discountPrice(18000)
                .title("시어서커 크롭 자켓 (다크네이비)")
                .quantityS(3)
                .quantityM(3)
                .quantityL(3)
                .category(ItemCategory.OUTWEAR)
                .build();
    }
    private List<Basket> baskets() {
        List<Basket> basketList = Arrays.asList(
                Basket.builder().id(1L).item(item()).user(user()).total(60000).count(2).size("S").build()
        );
        return basketList;
    }

}
