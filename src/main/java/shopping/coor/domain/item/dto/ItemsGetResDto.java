package shopping.coor.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.domain.item.Item;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class ItemsGetResDto {

    private Long itemId;
    private String title;
    private int price;
    private int discount_price;
    private List<ItemImageGetResDto> images;

    public ItemsGetResDto(Item items) {
        itemId = items.getId();
        title = items.getTitle();
        price = items.getPrice();
        discount_price = items.getDiscountPrice();
        images = items.getImages().stream()
                .map(image -> new ItemImageGetResDto(image))
                .collect(Collectors.toList());
    }


}
