package shopping.coor.order.presentation.http.response;

import lombok.*;
import shopping.coor.item.domain.image.Image;
import shopping.coor.order.domain.orderItem.OrderItem;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long orderItemId;
    private Long itemId;
    private List<Image> image;
    private String title;
    private String size;
    private int count;
    private int total;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.orderItemId = orderItem.getId();
        this.itemId = orderItem.getItem().getId();
        this.image = orderItem.getItem().getImages();
        this.title = orderItem.getItem().getTitle();
        this.count = orderItem.getOrderCount();
        this.total = orderItem.getTotalPrice();
        this.size = orderItem.getOrderSize();
    }
}
