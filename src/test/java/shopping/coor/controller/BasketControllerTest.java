//package shopping.coor.controller;
//
//import com.google.gson.Gson;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import shopping.coor.auth.presentation.http.request.MessageResponse;
//import shopping.coor.basket.application.service.BasketService;
//import shopping.coor.basket.presentation.http.BasketController;
//import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
//import shopping.coor.basket.presentation.http.response.BasketGetResDto;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class BasketControllerTest {
//
//    @InjectMocks
//    private BasketController basketController;
//
//    @Mock
//    private BasketService basketService;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void init() {
//        mockMvc = MockMvcBuilders.standaloneSetup(basketController).build();
//    }
//
//
//    @Test
//    public void 장바구니_추가() throws Exception {
//        // given
//        Long userId = 1L;
//        ResponseEntity<MessageResponse> responseEntity = ResponseEntity.status(HttpStatus.OK).body(messageResponse());
//        when(basketService.basketAddUser(any(), any())).thenReturn(responseEntity);
//        // when
//        ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/baskets")
//                        .contentType(APPLICATION_JSON)
//                        .content(new Gson().toJson(basketRequestDto())));
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("message",messageResponse().getMessage()).exists()).andReturn();
//    }
//
//    @Test
//    public void 장바구니_조회() throws Exception {
//        // given
//        Long userId = 1L;
//        when(basketService.getBasketByUserId(any())).thenReturn(basketResponseDto());
//        // when
//        ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.get("/api/baskets", userId)
//                        .contentType(APPLICATION_JSON)
//                        .content(new Gson().toJson(basketResponseDto())));
//
//        // then
//        result.andExpect(status().isOk()).andReturn();
//        assertEquals(60000, basketResponseDto().get(0).getItemTotal());
//        assertEquals(30000, basketResponseDto().get(1).getItemTotal());
//    }
//
//    @Test
//    public void 장바구니_삭제() throws Exception {
//        // given
//        Long basketId = 1L;
//        ResponseEntity responseEntity=new ResponseEntity(HttpStatus.OK);
//        when(basketService.removeBasketById(any(),any())).thenReturn(responseEntity);
//        // when
//        ResultActions result = mockMvc.perform(
//                MockMvcRequestBuilders.post("/api/basket/{basketId}", basketId));
//
//        // then
//        result.andExpect(status().isOk()).andReturn();
//    }
//
//
//    private List<BasketPostReqDto> basketRequestDto() {
//        List<BasketPostReqDto> basketPostReqDtoList = Arrays.asList(
//                BasketPostReqDto.builder().itemId(1L).itemTotal(60000).itemCount(2).size("S").title("시어서커 크롭 자켓 (다크네이비)").build(),
//                BasketPostReqDto.builder().itemId(1L).itemTotal(30000).itemCount(1).size("M").title("시어서커 크롭 자켓 (다크네이비)").build()
//        );
//        return basketPostReqDtoList;
//    }
//    private List<BasketGetResDto> basketResponseDto() {
//        List<BasketGetResDto> basketGetResDtoList = Arrays.asList(
//                BasketGetResDto.builder().itemId(1L).itemTotal(60000).itemCount(2).size("S").title("시어서커 크롭 자켓 (다크네이비)").build(),
//                BasketGetResDto.builder().itemId(1L).itemTotal(30000).itemCount(1).size("M").title("시어서커 크롭 자켓 (다크네이비)").build()
//        );
//        return basketGetResDtoList;
//    }
//    private MessageResponse messageResponse() {
//        return MessageResponse.builder()
//                .message("성공")
//                .build();
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
