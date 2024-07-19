package shopping.coor.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.coor.domain.basket.dto.BasketGetResDto;
import shopping.coor.domain.order.dto.OrderDeliveryPostReqDto;
import shopping.coor.domain.order.dto.OrderResponseDto;
import shopping.coor.common.MessageResponse;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/saveOrderDeliveryItem/{userId}")
    public ResponseEntity<MessageResponse> saveOrderDeliveryItem(@PathVariable Long userId, @RequestBody OrderDeliveryPostReqDto orderDeliveryPostReqDto) {
        return orderService.saveOrderDeliveryItem(userId, orderDeliveryPostReqDto);
    }

    @PostMapping("/quantityCheckOrder/{userId}")
    public ResponseEntity<MessageResponse> quantityCheckOrder(@PathVariable Long userId) {
        return orderService.quantityCheckOrder(userId);
    }

    @PostMapping("/soldOutItemRemove/{userId}")
    public List<BasketGetResDto> soldOutItemRemove(@PathVariable Long userId) {
        return orderService.soldOutItemRemove(userId);
    }

    @GetMapping("/getOrderUserById/{userId}")
    public List<OrderResponseDto> getOrderUserById(@PathVariable Long userId, @RequestParam String startDate,
                                                   @RequestParam String endDate, @RequestParam(required = false) String status) {
        return orderService.getOrderUserById(userId, startDate, endDate, status);
    }

    @PostMapping("/cancelOrderItem/{orderId}")
    public List<OrderResponseDto> cancelOrderItem(@PathVariable Long orderId, @RequestParam String startDate,
                                                  @RequestParam String endDate) {
        return orderService.cancelOrderItem(orderId, startDate, endDate);
    }
}













