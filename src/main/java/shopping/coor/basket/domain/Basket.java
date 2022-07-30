package shopping.coor.basket.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.item.domain.Item;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Basket {
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

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

    public Basket(User users, Item item, int itemTotal, int itemCount) {
        this.user=users;
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

    public Basket updateBasket(BasketPostReqDto dto) {
        this.itemTotal = this.itemTotal + dto.getItemTotal();
        this.itemCount = this.itemCount + dto.getItemCount();
        return this;
    }


}
