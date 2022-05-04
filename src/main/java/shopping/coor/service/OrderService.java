package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.model.Delivery;
import shopping.coor.model.Order;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.delivery.dto.DeliveryRequestDto;
import shopping.coor.repository.order.dto.OrderResponseDto;
import shopping.coor.repository.user.dto.MessageResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, DeliveryRequestDto deliveryRequestDto);
    ResponseEntity<MessageResponse> quantityCheckOrder(Long userId);
    List<BasketResponseDto> soldOutItemRemove(Long userId);
    List<OrderResponseDto> getOrderUserById(Long userId, String startDate, String endDate);
    List<OrderResponseDto> cancelOrderItem(Long orderId, String startDate, String endDate);

}
