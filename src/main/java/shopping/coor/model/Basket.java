package shopping.coor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Basket(User users, int itemTotal) {
        this.user=users;
        this.itemTotal = itemTotal;
    }



    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

//    public static Cart createCart(User user) {
//        Cart cart = new Cart();
//        cart.setCount(0);
//        cart.setUser(user);
//        return cart;
//    }
}
