package shopping.coor.payload.request;

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
public class BasketRequestDto {
    private Long keyIndex;

    private Long itemId;

    private int itemTotal;

    private int itemCount;

    private String size;

    private int discount;

    private List<Object> image;

    private int price;

    private String title;

}
