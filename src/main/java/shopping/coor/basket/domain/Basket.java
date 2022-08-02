package shopping.coor.basket.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.basket.presentation.http.request.BasketPutReqDto;
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

    public void updateBasket(int itemTotal, int itemCount) {
        this.itemTotal = this.itemTotal + itemTotal;
        this.itemCount = this.itemCount + itemCount;
    }
}
