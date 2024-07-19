package shopping.coor.domain.basket;

import lombok.*;
import org.hibernate.annotations.Where;
import shopping.coor.common.model.BaseEntityCreateUpdateAggregate;
import shopping.coor.domain.basket.dto.BasketPostReqDto;
import shopping.coor.domain.basket.dto.BasketPutReqDto;
import shopping.coor.domain.basket.enums.BasketOrder;
import shopping.coor.domain.item.Item;
import shopping.coor.domain.item.exception.BasketNotEnoughException;
import shopping.coor.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Basket extends BaseEntityCreateUpdateAggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="u_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name="i_id")
    private Item item;

    @Column(name = "total")
    private int total;

    @Column(name = "count")
    private int count;

    @Column(name = "size")
    private String size;



    public Basket(BasketPutReqDto dto, User user, Item items) {
        this.id = Long.parseLong(dto.getKeyIndex() + "" + user.getId());
        this.item = items;
        this.user = user;
        this.count = dto.getItemCount();
        this.total = dto.getItemTotal();
        this.size = dto.getSize();
    }

    public Basket(BasketPostReqDto dto, User user, Item items) {
        this.id = dto.getKeyIndex();
        this.user = user;
        this.item = items;
        this.count = dto.getItemCount();
        this.total = dto.getItemTotal();
        this.size = dto.getSize();
    }

    public Basket(User users, Item items, int itemTotal, int itemCount) {
        this.user = users;
        this.item = items;
        this.total = itemTotal;
        this.count = itemCount;
    }

    public static Basket createBasket(Long id, User user, Item items, int itemCount, int itemTotal, String size) {
        Basket basket = new Basket();
        basket.setId(id);
        basket.setUser(user);
        basket.setItem(items);
        basket.setCount(itemCount);
        basket.setTotal(itemTotal);
        basket.setSize(size);
        return basket;
    }

    // 회원이 장바구니 추가할 경우
    public void updateBasket(int itemTotal, int itemCount) {
        this.total = this.total + itemTotal;
        this.count = this.count + itemCount;
        this.item.stockCheck(this.count, this.size);
    }

    // 비회원이 장바구니 추가할 경우
    public void update(int itemTotal, int itemCount) {
        this.total = this.total + itemTotal;
        this.count = this.count + itemCount;
    }

    // 수량 조절
    public void updateCount(int itemTotal, BasketOrder order) {
        this.count = this.count + order.getNumber();

        if (order.name() == "UP") {
            this.total = this.total + itemTotal;
            this.item.stockCheck(this.count, this.size);
        } else if (order.name() == "DOWN") {
            this.total = this.total - itemTotal;
        }

        // -1 방지하기 위한 체크
        stockCheck(this.count);
    }

    private void stockCheck(int basketCount) {
        if (basketCount <= 0) {
            throw new BasketNotEnoughException();
        }
    }
}
