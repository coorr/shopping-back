package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.basket.presentation.http.response.BasketGetResDto;
import shopping.coor.repository.delivery.dto.DeliveryRequestDto;
import shopping.coor.repository.order.dto.OrderResponseDto;
import shopping.coor.auth.presentation.http.request.MessageResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, DeliveryRequestDto deliveryRequestDto);
    ResponseEntity<MessageResponse> quantityCheckOrder(Long userId);
    List<BasketGetResDto> soldOutItemRemove(Long userId);
    List<OrderResponseDto> getOrderUserById(Long userId, String startDate, String endDate, String status);
    List<OrderResponseDto> cancelOrderItem(Long orderId, String startDate, String endDate);

}
