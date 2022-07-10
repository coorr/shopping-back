package shopping.coor.repository.basket.dto;

import lombok.*;

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
