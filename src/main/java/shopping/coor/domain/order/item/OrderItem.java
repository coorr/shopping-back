package shopping.coor.domain.order.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.domain.basket.Basket;
import shopping.coor.domain.order.Order;
import shopping.coor.domain.item.Item;

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

    public static OrderItem createOrderItem(Basket basket) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(basket.getItem());
        orderItem.setOrderPrice(basket.getItemTotal());
        orderItem.setOrderCount(basket.getItemCount());
        orderItem.setOrderSize(basket.getSize());

        Item item = basket.getItem();
        item.removeStock(basket.getItemCount(), basket.getSize());
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
