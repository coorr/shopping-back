package shopping.coor.order.application.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.order.presentation.http.request.OrderDeliveryPostReqDto;
import shopping.coor.order.presentation.http.response.OrderResponseDto;
import shopping.coor.auth.presentation.http.request.MessageResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, OrderDeliveryPostReqDto orderDeliveryPostReqDto);
    ResponseEntity<MessageResponse> quantityCheckOrder(Long userId);
    List<BasketGetResDto> soldOutItemRemove(Long userId);
    List<OrderResponseDto> getOrderUserById(Long userId, String startDate, String endDate, String status);
    List<OrderResponseDto> cancelOrderItem(Long orderId, String startDate, String endDate);

}
