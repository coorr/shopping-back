package shopping.coor.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.coor.domain.user.User;
import shopping.coor.domain.item.Item;
import shopping.coor.domain.item.ItemRepository;
import shopping.coor.domain.order.Order;
import shopping.coor.domain.order.enums.OrderStatus;
import shopping.coor.domain.order.OrderRepository;
import shopping.coor.domain.delivery.Delivery;
import shopping.coor.domain.delivery.DeliveryStatus;
import shopping.coor.domain.order.item.OrderItem;
import shopping.coor.domain.order.item.OrderItemRepository;
import shopping.coor.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    String start = "20220504000000";
    String end = "20220204000000";


    @Test
    public void 주문_정보_가져오기() throws Exception {
        // given
        LocalDateTime startDate = stringToLocalDateTime(start);
        LocalDateTime endDate = stringToLocalDateTime(end);
        userRepository.save(user());
        itemRepository.save(item());
        Order orderList = orderRepository.save(orders().get(0));
        // when
        List<Order> result = orderRepository.getOrderUserById(user(), LocalDateTime.now(), endDate);

        // then
        assertEquals(result.get(0).getId(), orderList.getId());
    }

    private LocalDateTime stringToLocalDateTime(String start) {
        DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime startDateTimeChange= LocalDateTime.parse(start.substring(0,14),form);
        String stringStartDate = startDateTimeChange.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime result = LocalDateTime.parse(stringStartDate,formatter);

        return result;
    }

    private List<Order> orders() {
        List<Order> orders = Arrays.asList(
                Order.builder()
                        .id(1L)
                        .orderDate(stringToLocalDateTime("20220503000000"))
                        .status(OrderStatus.ORDER)
                        .user(user())
                        .orderItems(orderItems())
                        .delivery(delivery())
                        .build()
        );
        return orders;
    }

    private List<OrderItem> orderItems() {
        List<OrderItem> orderItems = Arrays.asList(
                OrderItem.builder().id(1L).orderCount(1).orderSize("S").orderPrice(18000).build(),
                OrderItem.builder().id(2L).orderCount(1).orderSize("M").orderPrice(18000).build()
        );
        return orderItems;
    }

    private User user() {
        return User.builder()
                .id(1L)
                .username("kim1")
                .email("W@naver.com")
                .password("123123")
                .build();
    }
    private Item item() {
        return Item.builder()
                .id(1L)
                .price(20000)
                .discountPrice(18000)
                .title("시어서커 크롭 자켓 (다크네이비)")
                .category("outwear")
                .quantityS(3)
                .quantityM(3)
                .quantityL(3)
                .build();
    }
    private Delivery delivery() {
        return Delivery.builder()
                .id(1L)
                .dName("kim")
                .dEmail("kim@naver.com")
                .roadNumber(66788)
                .address("서울특별시 강서후 방화동 250-43")
                .detailText("2323")
                .message(null)
                .status(DeliveryStatus.READY)
                .build();
    }

}
