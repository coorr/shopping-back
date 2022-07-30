package shopping.coor.basket.presentation.http.request;

import lombok.*;
import shopping.coor.auth.domain.User.User;
import shopping.coor.basket.domain.Basket;
import shopping.coor.item.domain.Item;

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
        return Basket.builder()
                .id(Long.parseLong(dto.getKeyIndex() + "" + user.getId()))
                .item(item)
                .user(user)
                .itemCount(dto.getItemCount())
                .itemTotal(dto.getItemTotal())
                .size(dto.getSize())
                .build();
    }
}
