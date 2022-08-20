package shopping.coor.item.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.domain.Basket;
import shopping.coor.item.application.exception.ItemNotFoundException;
import shopping.coor.item.domain.image.Image;
import shopping.coor.item.domain.Item;
import shopping.coor.item.domain.ItemRepository;
import shopping.coor.item.presentation.http.request.ImageUpdateReqDto;
import shopping.coor.item.presentation.http.request.ItemCreateReqDto;
import shopping.coor.item.presentation.http.request.ItemUpdateReqDto;
import shopping.coor.item.presentation.http.response.ItemGetResDto;
import shopping.coor.item.presentation.http.response.ItemsGetResDto;
import shopping.coor.order.domain.delivery.Delivery;
import shopping.coor.order.domain.delivery.DeliveryStatus;
import shopping.coor.order.domain.Order;
import shopping.coor.order.presentation.http.request.DeliveryPostReqDto;
import shopping.coor.item.domain.image.ImageRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class ItemServiceTest {
    @InjectMocks
    ItemService itemService;

    @Mock
    ItemRepository itemRepository;

    @Mock
    ImageRepository imageRepository;


    @Test
    @DisplayName("상품을 한 개 조회한다.")
    void getItem() throws Exception {
        given(itemRepository.findById(any())).willReturn(Optional.ofNullable(item()));

        ItemGetResDto result = itemService.getItem(1L);

        assertEquals(result.getItemId(), 1L);
        assertEquals(result.getTitle(), "시어서커 크롭 자켓 (다크네이비)");
    }

    @Test
    @DisplayName("메인 페이지에서 상품을 조회한다. 12개씩")
    void getItems() throws Exception {
        given(itemRepository.findByIdGreaterThanOrderByIdDesc(any(), any())).willReturn(items());

        List<ItemsGetResDto> result = itemService.getItems(0L, 12, "null");

        assertEquals(result.get(2).getTitle(), "시어서커 크롭 자켓 (화이트)");
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteItem() throws Exception {
        given(itemRepository.findById(any())).willReturn(Optional.ofNullable(item()));

        itemService.deleteItem(1L);

        verify(itemRepository).findById(1L);
    }

    @Test
    @DisplayName("상품을 생성한다.")
    void createItem() throws Exception {
        ItemCreateReqDto dto = ItemCreateReqDto.builder().price(20000).discountPrice(18000)
                .title("시어서커 크롭 자켓 (다크네이비)").quantityS(3).quantityM(3).quantityL(3).build();

        given(itemRepository.save(any())).willReturn(item());

        Long item = itemService.createItem(null, dto);

        assertEquals(item, 1);
    }

    @Test
    @DisplayName("상품을 조회했는데 없을 경우 예외가 터진다.")
    void getItemFail() throws Exception {
        assertThrows(ItemNotFoundException.class, () -> itemService.getItem(2L));
    }

    @Test
    @DisplayName("상품 정보를 수정한다.")
    void updateItem() throws Exception {
        ItemUpdateReqDto dto = ItemUpdateReqDto.builder().price(20000).discountPrice(18000)
                .title("시어서커 크롭 자켓 (테스트)").quantityS(3).quantityM(3).quantityL(3).imagePath(imageUpdateReqDto()).build();

        given(itemRepository.getItemList(any())).willReturn(item());
        doNothing().when(imageRepository).deleteAllByIdInBatch(any());

        Boolean result = itemService.updateItem(item().getId(), null, dto);

        assertEquals(result, true);
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
                .images(image())
                .build();
    }

    private List<ImageUpdateReqDto> imageUpdateReqDto() {
        return Arrays.asList(
                ImageUpdateReqDto.builder().id(1L).location("asdlkj-232").build(),
                ImageUpdateReqDto.builder().id(2L).location("asdlkj-232").build()
        );
    }

    private List<Image> image() {
        return Arrays.asList(
                Image.builder().id(1L).location("asdj-sdwew").build(),
                Image.builder().id(2L).location("asdj-sdwew").build(),
                Image.builder().id(3L).location("asdj-sdwew").build()
        );
    }
    private List<Item> items() {
        return Arrays.asList(
                Item.builder().id(1L).price(20000).discountPrice(18000).title("시어서커 크롭 자켓 (다크네이비)").quantityS(3).quantityM(3).quantityL(3).build(),
                Item.builder().id(2L).price(20000).discountPrice(18000).title("시어서커 크롭 자켓 (블랙)").quantityS(3).quantityM(3).quantityL(3).build(),
                Item.builder().id(3L).price(20000).discountPrice(18000).title("시어서커 크롭 자켓 (화이트)").quantityS(3).quantityM(3).quantityL(3).build()
        );
    }

    private List<Basket> basketList() {
        List<Basket> basketList = Arrays.asList(
                Basket.builder().item(item()).itemCount(2).itemTotal(30000).size("S").user(user()).build(),
                Basket.builder().item(item()).itemCount(2).itemTotal(530000).size("M").user(user()).build(),
                Basket.builder().item(item()).itemTotal(2).itemTotal(430000).size("L").user(user()).build()
        );
        return basketList;
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