package shopping.coor.repository.basket.dto;

import lombok.*;
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
@Builder
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
