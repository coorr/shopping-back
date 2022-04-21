package shopping.coor.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.coor.model.Image;
import shopping.coor.model.Item;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class ItemRequestDto {

    private Long itemId;
    private String title;
    private int price;
    private int discount_price;
    private String location;
    private List<ItemImageDto> images;

    public ItemRequestDto(Item item) {
        itemId = item.getId();
        title = item.getTitle();
        price = item.getPrice();
        discount_price = item.getDiscountPrice();
        images = item.getImages().stream()
                .map(image -> new ItemImageDto(image))
                .collect(Collectors.toList());
    }
}
