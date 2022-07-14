package shopping.coor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.item.domain.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int orderCount;

    private String orderSize;

    public static OrderItem createOrderItem(Item item, int orderPrice, int count, String size) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setOrderCount(count);
        orderItem.setOrderSize(size);

        item.removeStock(count, size);
        return orderItem;
    }

    public void cancel(String orderSize) {
        getItem().addStock(orderCount, orderSize);
    }

    // 주문상품 전체 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getOrderCount();
    }
}
