package shopping.coor.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shopping.coor.domain.user.UserDetailsImpl;
import shopping.coor.domain.basket.dto.BasketGetResDto;
import shopping.coor.common.container.SimpleBooleanResponse;
import shopping.coor.domain.order.OrderServiceTmp;
import shopping.coor.domain.order.OrderControllertmp;
import shopping.coor.domain.order.dto.OrderDeliveryPostReqDto;
import shopping.coor.domain.order.dto.OrderItemResponseDto;
import shopping.coor.domain.order.dto.OrderResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    @InjectMocks private OrderControllertmp orderController;

    @Mock private OrderServiceTmp orderService;

    private MockMvc mockMvc;

    OrderDeliveryPostReqDto orderDeliveryPostReqDto;
    UserDetailsImpl user;
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        user = UserDetailsImpl.builder().id(1L).build();

        orderDeliveryPostReqDto = OrderDeliveryPostReqDto.builder()
                .name("김진성")
                .email("wlsdiqkdrk@naver.com")
                .roadNumber(66778)
                .address("서울특별시 강서후 방화동 250-43")
                .detailText("201호")
                .message("22")
                .build();
    }


    @Test
    @DisplayName("주문을 생성한다.")
    public void createOrder() throws Exception {
        given(orderService.postOrder(any(), any())).willReturn(new SimpleBooleanResponse(true));

        Boolean result = orderController.postOrder(orderDeliveryPostReqDto, user).getBody().getResult();

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("주문 하기 전 상품 품절 체크를 한다.")
    public void beforeOrderItemSoldOutCheck() throws Exception {
        given(orderService.checkOrder(anyLong())).willReturn(null);

        String result = orderController.checkOrder(1L).getBody();

        assertThat(result).isNull();
    }




    private List<OrderResponseDto> orderResponseDto() {
        String str = "2022-05-04 01:55:40";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        List<OrderResponseDto> orderResponseDtoList = Arrays.asList(
                OrderResponseDto.builder().orderId(3L).orderDate(dateTime).orderItems(orderItemResponseDto()).build(),
                OrderResponseDto.builder().orderId(2L).orderDate(dateTime).orderItems(orderItemResponseDto()).build()
        );
        return orderResponseDtoList;
    }

    private List<OrderItemResponseDto> orderItemResponseDto() {
        List<OrderItemResponseDto> orderItemResponseDtoList = Arrays.asList(
                OrderItemResponseDto.builder().orderItemId(5L).count(1).size("S").title("시어서커 크롭 자켓 (다크네이비)").total(18000).build(),
                OrderItemResponseDto.builder().orderItemId(5L).count(2).size("M").title("시어서커 크롭 자켓 (다크네이비)").total(36000).build()
        );
        return orderItemResponseDtoList;
    }




    private SimpleBooleanResponse simpleBooleanResponse() {
        return SimpleBooleanResponse.builder()
                .result(true)
                .build();
    }

    private List<BasketGetResDto> basketResponseDto() {
        List<BasketGetResDto> basket = Arrays.asList(
                BasketGetResDto.builder().itemId(10L).itemTotal(30000).itemCount(5).size("L").discount(28000).build(),
                BasketGetResDto.builder().itemId(10L).itemTotal(530000).itemCount(4).size("S").discount(52000).build(),
                BasketGetResDto.builder().itemId(9L).itemTotal(430000).itemCount(3).size("L").discount(58000).build(),
                BasketGetResDto.builder().itemId(9L).itemTotal(230000).itemCount(2).size("M").discount(35000).build()
        );
        return basket;
    }
}




















