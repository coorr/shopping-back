package shopping.coor.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.coor.model.Basket;
import shopping.coor.model.Item;
import shopping.coor.model.User;
import shopping.coor.repository.basket.dto.BasketRequestDto;
import shopping.coor.repository.user.dto.LoginRequest;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.basket.BasketRepository;
import shopping.coor.repository.item.ItemRepository;
import shopping.coor.repository.user.UserRepository;
import shopping.coor.serviceImpl.BasketServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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


    @Test
    public void 장바구니_조회() throws Exception {
        // given
        Long userId = 1L;
        User user = new User();
        when(userRepository.getById(userId)).thenReturn(user);
        when(basketRepository.findAllByUserId(user)).thenReturn(basketList());

        // when
        List<BasketResponseDto> basketByUserId = basketService.getBasketByUserId(userId);

        // then
        assertEquals(5, basketByUserId.size());
    }

    @Test
    public void 장비추가_추가() throws Exception {
        // given
        Long user_id=1L;
        User user = new User();
        when(userRepository.getById(user_id)).thenReturn(user);
        ArrayList<Long> userSameKey = new ArrayList<Long>();
        userSameKey.add(1L);
        when(basketRepository.findArrayOnlyById(user)).thenReturn(userSameKey);
        Item item = new Item();
        Basket basket = Basket.builder()
                .item(item)
                .user(user)
                .itemTotal(30000)
                .itemCount(5)
                .build();
        when(basketRepository.getById(1L)).thenReturn(basket);
        when(itemRepository.getItemEntity(0L)).thenReturn(item);
        when(basketRepository.save(any())).thenReturn(basket);

        // when
        basketService.basketAddUser(user_id, basketRequestDto());

        // then
        assertEquals(basket.getItemTotal(), 330000);
        assertEquals(basket.getItemCount(), 10);
    }

    private List<Basket> basketList() {
        List<Basket> basketList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            basketList.add(new Basket(userList().get(i), itemList().get(i),  30000+i, 5));
        }
        return basketList;
    }

    private LoginRequest loginRequest() {
        return LoginRequest.builder()
                .username("test")
                .password("123123")
                .build();
    }

    private List<BasketRequestDto> basketRequestDto() {
        List<BasketRequestDto> basketRequestDtoList = new ArrayList<>();
        for (Long i = 0L; i < 5; i++) {
            basketRequestDtoList.add(new BasketRequestDto(i, i, 300000, 5, null, 28000, null, 30000, "옷"));
        }
        return basketRequestDtoList;
    }


    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        Long i = null;
        for (i = 0L; i < 5; i++) {
            userList.add(new User(i,"test", "test@naver.com", "123123"));
        }
        return userList;
    }

    private List<Item> itemList() {
        List<Item> itemList = new ArrayList<>();
        Long i = null;
        for (i = 0L; i < 5; i++) {
            itemList.add(new Item(i, "시어서커 블랙 바지(레드)", 9000, 5));
        }
        return itemList;
    }


}