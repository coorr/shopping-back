package shopping.coor.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import shopping.coor.model.Basket;
import shopping.coor.model.Image;
import shopping.coor.model.Item;
import shopping.coor.model.User;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
