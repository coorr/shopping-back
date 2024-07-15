package shopping.coor.domain.item.dto;

import lombok.Getter;
import shopping.coor.domain.item.Item;

@Getter
public class ImageUpdateResDto {
    private Long image_Id;
    private String location;
    private Item item_id;

}
