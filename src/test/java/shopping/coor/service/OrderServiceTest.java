package shopping.coor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shopping.coor.common.exception.NotEnoughStockException;
import shopping.coor.model.Basket;
import shopping.coor.model.Item;
import shopping.coor.model.Order;
import shopping.coor.model.User;
import shopping.coor.repository.basket.BasketRepository;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.delivery.dto.DeliveryRequestDto;
import shopping.coor.repository.item.ItemRepository;
import shopping.coor.repository.order.OrderRepository;
import shopping.coor.repository.user.UserRepository;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.serviceImpl.BasketServiceImpl;
import shopping.coor.serviceImpl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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

    @Test
    public void 상품_주문_생성() throws Exception {
        // given
        User user = user();
        when(userRepository.getById(1L)).thenReturn(user);
        when(basketRepository.findAllByUserId(any(User.class))).thenReturn(basketList());

        // when
        ResponseEntity<MessageResponse> responseEntity = orderService.saveOrderDeliveryItem(user.getId(), deliveryRequestDto());

        assertEquals(responseEntity, null);
        // then
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

    

    private List<BasketResponseDto> basketResponseDto() {
        List<BasketResponseDto> basketResponseDtoList = Arrays.asList(
                BasketResponseDto.builder().itemId(10L).itemTotal(30000).itemCount(5).size("L").discount(28000).build(),
                BasketResponseDto.builder().itemId(10L).itemTotal(530000).itemCount(4).size("S").discount(52000).build(),
                BasketResponseDto.builder().itemId(9L).itemTotal(430000).itemCount(3).size("L").discount(58000).build(),
                BasketResponseDto.builder().itemId(9L).itemTotal(230000).itemCount(2).size("M").discount(35000).build()
        );
        return basketResponseDtoList;
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
    private DeliveryRequestDto deliveryRequestDto() {
        return DeliveryRequestDto.builder()
                .name("김진성")
                .email("wlsdiqkdrk@naver.com")
                .roadNumber(66778)
                .address("서울특별시 강서후 방화동 250-43")
                .detailText("201호")
                .message("22")
                .build();
    }
}