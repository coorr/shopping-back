package shopping.coor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import shopping.coor.auth.domain.User.User;
import shopping.coor.auth.domain.User.UserRepository;
import shopping.coor.auth.presentation.http.request.MessageResponse;
import shopping.coor.basket.domain.Basket;
import shopping.coor.basket.domain.BasketRepository;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.item.domain.Item;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.model.Order;
import shopping.coor.serviceImpl.BasketServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @InjectMocks
    BasketServiceImpl basketService;
    
    @Mock
    BasketRepository basketRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    void init() {
        User.builder()
                .id(1L)
                .username("kim")
                .email("kim@naver.com")
                .build();

        Item.builder()
                .id(2L)
                .title("시어서커 자켓 블랙진(다크네이비)")
                .price(100000)
                .discountPrice(90000)
                .build();
    }

    @DisplayName("장바구니_조회")
    @Test
    public void getBasketByUserId() throws Exception {
        // given
        Long userId = 1L;
        User user = new User();
        when(userRepository.getById(userId)).thenReturn(user);
        when(basketRepository.findAllByUserId(user)).thenReturn(basketList());

        // when
        List<BasketGetResDto> basketByUserId = basketService.getBasketByUserId(userId);

        // then
        assertEquals(2, basketByUserId.size());
    }

    @DisplayName("장바구니_아이템_추가")
    @Test
    public void basketAddUser_추가() throws Exception {
        // given
        Basket basket = basketList().get(0);
        when(userRepository.getById(any())).thenReturn(user());
        when(itemRepository.getById(any())).thenReturn(item());
        when(itemRepository.findQuantitySizeSCount(any())).thenReturn(5);
        when(basketRepository.save(any())).thenReturn(basket);

        // when
        ResponseEntity<MessageResponse> result = basketService.basketAddUser(user().getId(), basketRequestDto());

        // then
        assertEquals(null, result);
    }

    @DisplayName("장바구니_아이템_추가_품절예외")
    @Test
    public void basketAddUser_예외() throws Exception {
        // given
        when(userRepository.getById(any())).thenReturn(user());
        when(itemRepository.getById(any())).thenReturn(item());
        when(itemRepository.findQuantitySizeSCount(any())).thenReturn(3);
        when(itemRepository.findQuantitySizeMCount(any())).thenReturn(3);
        // when
        ResponseEntity<MessageResponse> result = basketService.basketAddUser(user().getId(), basketRequestDtoOVer());

        // then
        String errorMessage = String.format("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : %s", item().getTitle());
        assertEquals(400, result.getStatusCodeValue());
        assertEquals(errorMessage , result.getBody().getMessage());
    }

    @DisplayName("장바구니_품절체크")
    @Test
    public void duplicateSizeQuantityCheck() throws Exception {
        // given
        when(itemRepository.getById(any())).thenReturn(item());
        when(itemRepository.findQuantitySizeSCount(any())).thenReturn(1);

        // when
        ResponseEntity<MessageResponse> result = basketService.duplicateSizeQuantityCheck(basketRequestDto());

        // then
        String errorMessage = String.format("상품의 수량이 재고수량 보다 많습니다. \n\n제품명 : %s", item().getTitle());
        assertEquals(400, result.getStatusCodeValue());
        assertEquals(errorMessage, result.getBody().getMessage());
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
    private Item item() {
        return Item.builder()
                .id(1L)
                .price(20000)
                .discountPrice(18000)
                .title("시어서커 크롭 자켓 (다크네이비)")
                .quantityS(3)
                .quantityM(3)
                .quantityL(3)
                .category("outwear")
                .build();
    }
    private List<Basket> basketList() {
        List<Basket> basketList = Arrays.asList(
                Basket.builder().id(1L).item(item()).user(user()).itemTotal(60000).itemCount(2).size("S").build(),
                Basket.builder().id(2L).item(item()).user(user()).itemTotal(30000).itemCount(1).size("M").build()
        );
        return basketList;
    }

    private List<BasketPostReqDto> basketRequestDtoOVer() {
        List<BasketPostReqDto> basketPostReqDtoList = Arrays.asList(
                BasketPostReqDto.builder().itemId(1L).itemTotal(30000).itemCount(2).size("S").discount(28000).price(15000).title("시어서커(다크 네이비)").build(),
                BasketPostReqDto.builder().itemId(1L).itemTotal(1500000).itemCount(50).size("M").discount(80000).price(30000).title("시어서커(다크 네이비)").build()
        );
        return basketPostReqDtoList;
    }

    private List<BasketPostReqDto> basketRequestDto() {
        List<BasketPostReqDto> basketPostReqDtoList = Arrays.asList(
                BasketPostReqDto.builder().itemId(1L).itemTotal(30000).itemCount(2).size("S").discount(28000).price(15000).title("시어서커(다크 네이비)").build()
        );
        return basketPostReqDtoList;
    }

}