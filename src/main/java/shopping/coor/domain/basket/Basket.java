package shopping.coor.domain.basket;

import lombok.*;
import org.hibernate.annotations.Where;
import shopping.coor.domain.user.User;
import shopping.coor.domain.basket.dto.BasketPostReqDto;
import shopping.coor.domain.basket.dto.BasketPutReqDto;
import shopping.coor.common.BaseEntityAggregateRoot;
import shopping.coor.domain.basket.enums.BasketOrder;
import shopping.coor.domain.item.exception.NotEnoughBasketException;
import shopping.coor.domain.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Basket extends BaseEntityAggregateRoot<Basket> {
    @Id
    @Column(name = "basket_id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private int itemTotal;

    private int itemCount;

    private String size;



    public Basket(BasketPutReqDto dto, User user, Item item) {
        this.id = Long.parseLong(dto.getKeyIndex() + "" + user.getId());
        this.item = item;
        this.user = user;
        this.itemCount = dto.getItemCount();
        this.itemTotal = dto.getItemTotal();
        this.size = dto.getSize();
    }

    public Basket(BasketPostReqDto dto, User user, Item item) {
        this.id = dto.getKeyIndex();
        this.user = user;
        this.item = item;
        this.itemCount = dto.getItemCount();
        this.itemTotal = dto.getItemTotal();
        this.size = dto.getSize();
    }

    public Basket(User users, Item item, int itemTotal, int itemCount) {
        this.user = users;
        this.item = item;
        this.itemTotal = itemTotal;
        this.itemCount = itemCount;
    }

    public static Basket createBasket(Long id, User user, Item item, int itemCount, int itemTotal, String size) {
        Basket basket = new Basket();
        basket.setId(id);
        basket.setUser(user);
        basket.setItem(item);
        basket.setItemCount(itemCount);
        basket.setItemTotal(itemTotal);
        basket.setSize(size);
        return basket;
    }

    // 회원이 장바구니 추가할 경우
    public void updateBasket(int itemTotal, int itemCount) {
        this.itemTotal = this.itemTotal + itemTotal;
        this.itemCount = this.itemCount + itemCount;
        this.item.stockCheck(this.itemCount, this.size);
    }

    // 비회원이 장바구니 추가할 경우
    public void update(int itemTotal, int itemCount) {
        this.itemTotal = this.itemTotal + itemTotal;
        this.itemCount = this.itemCount + itemCount;
    }

    // 수량 조절
    public void updateCount(int itemTotal, BasketOrder order) {
        this.itemCount = this.itemCount + order.getNumber();

        if (order.name() == "UP") {
            this.itemTotal = this.itemTotal + itemTotal;
            this.item.stockCheck(this.itemCount, this.size);
        } else if (order.name() == "DOWN") {
            this.itemTotal = this.itemTotal - itemTotal;
        }

        // -1 방지하기 위한 체크
        stockCheck(this.itemCount);
    }

    private void stockCheck(int basketCount) {
        if (basketCount <= 0) {
            throw new NotEnoughBasketException();
        }
    }
}
