package shopping.coor.order.presentation.http.response;

import lombok.*;
import shopping.coor.order.domain.delivery.DeliveryStatus;
import shopping.coor.order.domain.Order;
import shopping.coor.order.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;
    private List<OrderItemResponseDto> orderItems;

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getStatus();
        this.deliveryStatus = order.getDelivery().getStatus();
        this.orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemResponseDto(orderItem))
                .collect(Collectors.toList());
    }
}
