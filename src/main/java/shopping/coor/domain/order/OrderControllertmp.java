package shopping.coor.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shopping.coor.domain.user.UserDetailsImpl;
import shopping.coor.common.container.SimpleBooleanResponse;
import shopping.coor.domain.order.dto.OrderDeliveryCreateReqDto;
import shopping.coor.domain.order.dto.OrderDeliveryPostReqDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderControllertmp {

    private final OrderServiceTmp orderService;

    @PostMapping
    public ResponseEntity<SimpleBooleanResponse> postOrder(@RequestBody @Valid OrderDeliveryPostReqDto dto,
                                                           @AuthenticationPrincipal UserDetailsImpl user) {
        return ResponseEntity.ok().body(orderService.postOrder(user.getId(), new OrderDeliveryCreateReqDto(dto)));
    }

    @PostMapping("/check/")
    public ResponseEntity<String> checkOrder(@PathVariable Long userId) {
        return ResponseEntity.ok().body(orderService.checkOrder(userId));
    }
//
//    @PostMapping("/soldOutItemRemove/{userId}")
//    public List<BasketGetResDto> soldOutItemRemove(@PathVariable Long userId) {
//        return orderService.soldOutItemRemove(userId);
//    }
//
//    @GetMapping("/getOrderUserById/{userId}")
//    public List<OrderResponseDto> getOrderUserById(@PathVariable Long userId, @RequestParam String startDate,
//                                                   @RequestParam String endDate, @RequestParam(required = false) String status) {
//        return orderService.getOrderUserById(userId, startDate, endDate, status);
//    }
//
//    @PostMapping("/cancelOrderItem/{orderId}")
//    public List<OrderResponseDto> cancelOrderItem(@PathVariable Long orderId, @RequestParam String startDate,
//                                                  @RequestParam String endDate) {
//        return orderService.cancelOrderItem(orderId, startDate, endDate);
//    }
}













