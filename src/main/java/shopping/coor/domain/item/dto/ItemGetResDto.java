package shopping.coor.domain.item.dto;

import lombok.*;
import shopping.coor.domain.item.Item;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public ItemGetResDto(Item items) {
        this.itemId = items.getId();
        this.title = items.getTitle();
        this.price = items.getPrice();
        this.discount_price = items.getDiscountPrice();
        this.quantityS = items.getQuantityS();
        this.quantityM = items.getQuantityM();
        this.quantityL = items.getQuantityL();
        this.category = items.getCategory();
        this.size = items.getSizeInfo();
        this.material = items.getMaterial();
        this.info = items.getInfo();
        this.images = items.getImages().stream()
                .map(i -> new ItemImageGetResDto(i))
                .collect(Collectors.toList());
    }


}
