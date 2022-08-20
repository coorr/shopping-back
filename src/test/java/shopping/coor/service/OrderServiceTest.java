package shopping.coor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.domain.Basket;
import shopping.coor.item.application.exception.NotEnoughStockException;
import shopping.coor.item.domain.Item;
import shopping.coor.basket.domain.BasketRepository;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.order.domain.*;
import shopping.coor.order.domain.delivery.Delivery;
import shopping.coor.order.domain.delivery.DeliveryStatus;
import shopping.coor.order.domain.orderItem.OrderItem;
import shopping.coor.order.presentation.http.request.DeliveryPostReqDto;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.order.domain.OrderRepository;
import shopping.coor.order.presentation.http.response.OrderItemResponseDto;
import shopping.coor.order.presentation.http.response.OrderResponseDto;
import shopping.coor.auth.domain.User.UserRepository;
import shopping.coor.auth.presentation.http.request.MessageResponse;
import shopping.coor.order.application.service.OrderServiceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    BasketRepository basketRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    String start = "20220504000000";
    String end = "20220204000000";

    @Test
    public void 상품_주문_생성() throws Exception {
        // given
        when(userRepository.getById(1L)).thenReturn(user());
        when(basketRepository.findAllByUserId(any(User.class))).thenReturn(basketList());

        // when
        ResponseEntity<MessageResponse> responseEntity = orderService.saveOrderDeliveryItem(user().getId(), deliveryRequestDto());

        // then
        assertEquals(responseEntity, null);
    }

    @Test
    public void 상품_주문_품절_예외() throws Exception {
        // given
        User user = user();
        List<Basket> basketList = Arrays.asList(
                Basket.builder().item(item()).itemCount(4).itemTotal(30000).size("S").user(user()).build(),
                Basket.builder().item(item()).itemCount(2).itemTotal(530000).size("M").user(user()).build(),
                Basket.builder().item(item()).itemTotal(2).itemTotal(430000).size("L").user(user()).build()
        );
        when(userRepository.getById(1L)).thenReturn(user);
        when(basketRepository.findAllByUserId(any(User.class))).thenReturn(basketList);

        // when
        assertThrows(NotEnoughStockException.class, () -> orderService.saveOrderDeliveryItem(user.getId(), deliveryRequestDto()));
    }

    @Test
    public void 오더_상품_품질_체크() throws Exception {
        // given
        User user = user();
        List<Basket> basketList = Arrays.asList(
                Basket.builder().item(item()).itemCount(3).itemTotal(30000).size("S").user(user()).build(),
                Basket.builder().item(item()).itemCount(2).itemTotal(530000).size("M").user(user()).build(),
                Basket.builder().item(item()).itemTotal(2).itemTotal(430000).size("L").user(user()).build()
        );
        when(userRepository.getById(1L)).thenReturn(user);
        when(basketRepository.findAllByUserId(any(User.class))).thenReturn(basketList);
        when(itemRepository.findQuantitySizeSCount(1L)).thenReturn(2);
        when(itemRepository.findQuantitySizeMCount(1L)).thenReturn(3);
        when(itemRepository.findQuantitySizeLCount(1L)).thenReturn(3);

        // when
        ResponseEntity<MessageResponse> responseEntity = orderService.quantityCheckOrder(user.getId());

        // then
        assertEquals("시어서커 크롭 자켓 (다크네이비) S\n", responseEntity.getBody().getMessage());
    }

    @Test
    public void 오더_상품갯수_품질갯수_예외() throws Exception {
        // given
        User user = user();
        List<Basket> basketList = Arrays.asList(
                Basket.builder().item(item()).itemCount(3).itemTotal(30000).size("S").user(user()).build(),
                Basket.builder().item(item()).itemCount(3).itemTotal(530000).size("M").user(user()).build(),
                Basket.builder().item(item()).itemCount(3).itemTotal(430000).size("L").user(user()).build()
        );
        when(userRepository.getById(1L)).thenReturn(user);
        when(basketRepository.findAllByUserId(any(User.class))).thenReturn(basketList);
        when(itemRepository.findQuantitySizeSCount(1L)).thenReturn(2);
        when(itemRepository.findQuantitySizeMCount(1L)).thenReturn(2);
        when(itemRepository.findQuantitySizeLCount(1L)).thenReturn(2);

        // when
        ResponseEntity<MessageResponse> responseEntity = orderService.quantityCheckOrder(user.getId());

        // then
        assertEquals(responseEntity.getBody().getMessage(), "품절된 상품으로 주문할 수 없습니다.");
    }


    @Test
    public void 상품_내역_조회() throws Exception {
        // given
        Long userId = 1L;
        String status = "";
        User user = user();
        when(userRepository.getById(any())).thenReturn(user);
        when(orderRepository.getOrderUserById(any(), any(),  any())).thenReturn(orders());
        // when
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrderUserById(userId, start, end, status);
        // then
        assertEquals(orders().get(0).getOrderItems().get(0).getOrderSize(), orderResponseDtoList.get(0).getOrderItems().get(0).getSize(), "사이즈 비교");
        assertEquals(orders().get(0).getOrderItems().get(0).getOrderCount(), orderResponseDtoList.get(0).getOrderItems().get(0).getCount(), "상품 수량 비교");
        assertEquals(orders().get(0).getOrderItems().get(0).getOrderPrice(), orderResponseDtoList.get(0).getOrderItems().get(0).getTotal(), "상품 가격 비교");
    }


    @Test
    public void 주문_취소() throws Exception {
        // given
        Long orderId = 1L;
        when(orderRepository.getById(orderId)).thenReturn(orders().get(0));
        when(orderRepository.getOrderUserById(any(), any(), any())).thenReturn(ordersCancel());
        // when
        List<OrderResponseDto> result = orderService.cancelOrderItem(orders().get(0).getId(), start, end);
        // then
        assertEquals(result.get(0).getOrderStatus(), ordersCancel().get(0).getStatus(), "취소 확인 상태");
    }
    
    @Test
    public void 주문_취소_예외() throws Exception {
        // given
        Long orderId = 1L;
        when(orderRepository.getById(orderId)).thenReturn(ordersCancel().get(0));
        // when // then
        assertThrows(IllegalStateException.class, () -> orderService.cancelOrderItem(ordersCancel().get(0).getId(), start, end));
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
                Order.builder().id(1L).orderDate(stringToLocalDateTime(start)).status(OrderStatus.ORDER).user(user()).orderItems(orderItems()).delivery(delivery()).build()
        );
        return orders;
    }

    private List<Order> ordersCancel() {
        List<Order> orders = Arrays.asList(
                Order.builder().id(1L).orderDate(stringToLocalDateTime(start)).status(OrderStatus.CANCEL).user(user()).orderItems(orderItems()).delivery(deliveryComp()).build()
        );
        return orders;
    }

    private List<OrderItem> orderItems() {
        List<OrderItem> orderItems = Arrays.asList(
                OrderItem.builder().id(5L).item(item()).orderCount(1).orderSize("S").orderPrice(18000).build(),
                OrderItem.builder().id(6L).item(item()).orderCount(1).orderSize("M").orderPrice(18000).build()
        );
        return orderItems;
    }

    private List<OrderItemResponseDto> orderItemResponseDto() {
        List<OrderItemResponseDto> orderItemResponseDtoList = Arrays.asList(
                OrderItemResponseDto.builder().orderItemId(5L).count(1).size("S").title("시어서커 크롭 자켓 (다크네이비)").total(18000).image(null).build(),
                OrderItemResponseDto.builder().orderItemId(5L).count(2).size("M").title("시어서커 크롭 자켓 (다크네이비)").total(36000).image(null).build()
        );
        return orderItemResponseDtoList;
    }

    private List<BasketGetResDto> basketResponseDto() {
        List<BasketGetResDto> basketGetResDtoList = Arrays.asList(
                BasketGetResDto.builder().itemId(10L).itemTotal(30000).itemCount(5).size("L").discount(28000).build(),
                BasketGetResDto.builder().itemId(10L).itemTotal(530000).itemCount(4).size("S").discount(52000).build(),
                BasketGetResDto.builder().itemId(9L).itemTotal(430000).itemCount(3).size("L").discount(58000).build(),
                BasketGetResDto.builder().itemId(9L).itemTotal(230000).itemCount(2).size("M").discount(35000).build()
        );
        return basketGetResDtoList;
    }

    private User user() {
        List<Order> orders = new ArrayList<>();
        return User.builder()
                .id(1L)
                .username("kim1")
                .email("W@naver.com")
                .password("123123")
                .orders(orders)
                .build();
    }
    private List<Basket> basketList() {
        List<Basket> basketList = Arrays.asList(
                Basket.builder().item(item()).itemCount(2).itemTotal(30000).size("S").user(user()).build(),
                Basket.builder().item(item()).itemCount(2).itemTotal(530000).size("M").user(user()).build(),
                Basket.builder().item(item()).itemTotal(2).itemTotal(430000).size("L").user(user()).build()
        );
        return basketList;
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
                .build();
    }
    private DeliveryPostReqDto deliveryRequestDto() {
        return DeliveryPostReqDto.builder()
                .name("김진성")
                .email("wlsdiqkdrk@naver.com")
                .roadNumber(66778)
                .address("서울특별시 강서후 방화동 250-43")
                .detailText("201호")
                .message("22")
                .build();
    }

    private Delivery delivery() {
        return Delivery.builder()
                .status(DeliveryStatus.READY)
                .build();
    }

    private Delivery deliveryComp() {
        return Delivery.builder()
                .status(DeliveryStatus.COMP)
                .build();
    }

}