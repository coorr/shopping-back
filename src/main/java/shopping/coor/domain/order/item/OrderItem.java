package shopping.coor.domain.order.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.domain.basket.Basket;
import shopping.coor.domain.item.Item;
import shopping.coor.domain.order.Order;

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
    @JoinColumn(name = "iid")
    private Item items;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int orderCount;

    private String orderSize;

    public static OrderItem createOrderItem(Basket basket) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItems(basket.getItem());
        orderItem.setOrderPrice(basket.getTotal());
        orderItem.setOrderCount(basket.getCount());
        orderItem.setOrderSize(basket.getSize());

        Item items = basket.getItem();
        items.removeStock(basket.getCount(), basket.getSize());
        return orderItem;
    }

    public void cancel(String orderSize) {
        getItems().addStock(orderCount, orderSize);
    }

    // 주문상품 전체 가격 조회
    public int getTotalPrice() {
        return getOrderPrice() * getOrderCount();
    }
}
