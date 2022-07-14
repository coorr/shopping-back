package shopping.coor.item.presentation.http.response;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.item.domain.Item;

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

    public ItemsGetResDto(Item item) {
        itemId = item.getId();
        title = item.getTitle();
        price = item.getPrice();
        discount_price = item.getDiscountPrice();
        images = item.getImages().stream()
                .map(image -> new ItemImageGetResDto(image))
                .collect(Collectors.toList());
    }


}
