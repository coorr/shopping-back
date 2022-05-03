package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.model.Delivery;
import shopping.coor.repository.basket.dto.BasketResponseDto;
import shopping.coor.repository.delivery.dto.DeliveryRequestDto;
import shopping.coor.repository.user.dto.MessageResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, DeliveryRequestDto deliveryRequestDto);
    ResponseEntity<MessageResponse> quantityCheckOrder(Long userId);
    List<BasketResponseDto> soldOutItemRemove(Long userId);

}
