package shopping.coor.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shopping.coor.model.Image;
import shopping.coor.model.User;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.delivery.dto.DeliveryRequestDto;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }


    @Test
    public void 주문_생성() throws Exception {
        // given
        Long userId = 1L;
        DeliveryRequestDto deliveryRequestDto = deliveryRequestDto();
        ResponseEntity<MessageResponse> responseEntity = ResponseEntity.status(HttpStatus.OK).body(messageResponse());

        when(orderService.saveOrderDeliveryItem(any(), any(DeliveryRequestDto.class))).thenReturn(responseEntity);
        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/order/saveOrderDeliveryItem/{userId}", userId)
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(deliveryRequestDto))
        );
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("message", messageResponse().getMessage()).exists()).andReturn();
    }

    @Test
    public void 주문_상품_품절_체크() throws Exception {
        // given
        Long userId = 1L;
        ResponseEntity<MessageResponse> responseEntity = ResponseEntity.status(HttpStatus.OK).body(messageResponse());

        when(orderService.quantityCheckOrder(any())).thenReturn(responseEntity);
        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/order/quantityCheckOrder/{userId}", userId)
        );
        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("message", messageResponse().getMessage()).exists()).andReturn();
    }
    
    @Test
    public void 상품_품절_삭제() throws Exception {
        // given
        Long userId = 1L;
        List<BasketResponseDto> basketResponseDto = basketResponseDto();
        when(orderService.soldOutItemRemove(any())).thenReturn(basketResponseDto());
        // when
        ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/order/soldOutItemRemove/{userId}", userId)
        );
        // then
        result.andExpect(status().isOk()).andReturn();
        assertEquals(basketResponseDto.size(), 4);
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

    private MessageResponse messageResponse() {
        return MessageResponse.builder()
                .message("성공")
                .build();
    }

    private List<BasketResponseDto> basketResponseDto() {
        List<BasketResponseDto> basket = Arrays.asList(
                BasketResponseDto.builder().itemId(10L).itemTotal(30000).itemCount(5).size("L").discount(28000).build(),
                BasketResponseDto.builder().itemId(10L).itemTotal(530000).itemCount(4).size("S").discount(52000).build(),
                BasketResponseDto.builder().itemId(9L).itemTotal(430000).itemCount(3).size("L").discount(58000).build(),
                BasketResponseDto.builder().itemId(9L).itemTotal(230000).itemCount(2).size("M").discount(35000).build()
        );
        return basket;
    }
}




















