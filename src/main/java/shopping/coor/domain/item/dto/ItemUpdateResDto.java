package shopping.coor.domain.item.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ItemUpdateResDto {
    private String title;
    private int price;
    private int discountPrice;
    private int quantityS;
    private int quantityM;
    private int quantityL;
    private String category;
    private String size;
    private String material;
    private String info;
}
