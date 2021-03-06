package shopping.coor.repository.order.dto;

import lombok.*;
import shopping.coor.model.Image;
import shopping.coor.model.OrderItem;

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
