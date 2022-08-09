package shopping.coor.basket.presentation.http.response;

import lombok.*;
import shopping.coor.item.domain.image.Image;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketPatchResDto {
    private Long keyIndex;
    private Long itemId;
    private int itemTotal;
    private int itemCount;
    private String size;
    private int discount;
    private int price;
    private String title;

    private List<Image> image;
}
