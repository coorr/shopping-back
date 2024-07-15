package shopping.coor.domain.basket.dto;

import lombok.*;
import shopping.coor.domain.user.User;
import shopping.coor.domain.basket.Basket;
import shopping.coor.domain.item.Item;

import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketPostReqDto {
    @NotNull
    private Long keyIndex;

    @NotNull
    private Long itemId;

    @NotNull
    private int itemTotal;

    @NotNull
    private int itemCount;

    @NotNull
    private String size;

    @NotNull
    private int discount;

    private List<Object> image;

    @NotNull
    private int price;

    @NotNull
    private String title;

    public Basket toBasket(BasketPostReqDto dto, User user, Item item) {
        return new Basket(dto, user, item);
    }
}
