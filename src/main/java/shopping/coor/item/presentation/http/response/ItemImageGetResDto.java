package shopping.coor.item.presentation.http.response;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.model.Image;

@Getter
@Setter
public class ItemImageGetResDto {

    private Long id;
    private String location;

    public ItemImageGetResDto(Image image) {
        id = image.getId();
        location =image.getLocation();
    }
}
