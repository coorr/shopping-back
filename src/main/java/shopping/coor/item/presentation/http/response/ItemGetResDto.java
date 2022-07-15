package shopping.coor.item.presentation.http.response;

import lombok.*;
import shopping.coor.item.domain.Item;

import java.util.List;
import java.util.stream.Collectors;

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

    public ItemGetResDto(Item item) {
        this.itemId = item.getId();
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.discount_price = item.getDiscountPrice();
        this.quantityS = item.getQuantityS();
        this.quantityM = item.getQuantityM();
        this.quantityL = item.getQuantityL();
        this.category = item.getCategory();
        this.size = item.getSize();
        this.material = item.getCategory();
        this.info = item.getInfo();
        this.images = item.getImages().stream()
                .map(i -> new ItemImageGetResDto(i))
                .collect(Collectors.toList());
    }


}
