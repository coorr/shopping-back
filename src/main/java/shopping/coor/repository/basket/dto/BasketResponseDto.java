package shopping.coor.repository.basket.dto;

import lombok.*;
import shopping.coor.model.Basket;
import shopping.coor.model.Image;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketResponseDto {
    private Long keyIndex;

    private Long itemId;

    private int itemTotal;

    private int itemCount;

    private String size;

    private int discount;

    private List<Image> image;

    private int price;

    private String title;

    public BasketResponseDto(Basket basket) {
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
