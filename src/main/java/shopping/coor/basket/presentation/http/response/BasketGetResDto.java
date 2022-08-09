package shopping.coor.basket.presentation.http.response;

import lombok.*;
import shopping.coor.basket.domain.Basket;
import shopping.coor.item.domain.image.Image;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketGetResDto {
    private Long keyIndex;
    private Long itemId;
    private int itemTotal;
    private int itemCount;
    private String size;
    private int discount;
    private int price;
    private String title;

    private List<Image> image;

    public BasketGetResDto(Basket basket) {
        this.keyIndex = basket.getId();
        this.itemId = basket.getItem().getId();
        this.itemTotal = basket.getItemTotal();
        this.itemCount = basket.getItemCount();
        this.size = basket.getSize();
        this.discount = basket.getItem().getDiscountPrice();
        this.image = basket.getItem().getImages();
        this.price = basket.getItem().getPrice();
        this.title = basket.getItem().getTitle();
    }
}
