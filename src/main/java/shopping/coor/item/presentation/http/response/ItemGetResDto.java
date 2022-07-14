package shopping.coor.item.presentation.http.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class ItemGetResDto {

    private Long itemId;
    private String title;
    private int price;
    private int discount_price;
    private int quantityS;
    private int quantityM;
    private int quantityL;
    private String category;
    private String size;
    private String material;
    private String info;
    private List<ItemImageGetResDto> images;




}
