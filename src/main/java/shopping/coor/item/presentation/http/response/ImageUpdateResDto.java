package shopping.coor.item.presentation.http.response;

import lombok.Getter;
import shopping.coor.item.domain.Item;

@Getter
public class ImageUpdateResDto {
    private Long image_Id;
    private String location;
    private Item item_id;

}
