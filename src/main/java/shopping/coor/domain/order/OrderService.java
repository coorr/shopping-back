package shopping.coor.domain.order;

import org.springframework.http.ResponseEntity;
import shopping.coor.domain.basket.dto.BasketGetResDto;
import shopping.coor.domain.order.dto.OrderDeliveryPostReqDto;
import shopping.coor.domain.order.dto.OrderResponseDto;
import shopping.coor.common.MessageResponse;

import java.util.List;

public interface OrderService {
    ResponseEntity<MessageResponse> saveOrderDeliveryItem(Long userId, OrderDeliveryPostReqDto orderDeliveryPostReqDto);
    ResponseEntity<MessageResponse> quantityCheckOrder(Long userId);
    List<BasketGetResDto> soldOutItemRemove(Long userId);
    List<OrderResponseDto> getOrderUserById(Long userId, String startDate, String endDate, String status);
    List<OrderResponseDto> cancelOrderItem(Long orderId, String startDate, String endDate);

}
