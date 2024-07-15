package shopping.coor.domain.order.dto;

import lombok.*;
import shopping.coor.domain.item.image.Image;
import shopping.coor.domain.order.item.OrderItem;

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
