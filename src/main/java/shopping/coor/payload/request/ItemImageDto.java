package shopping.coor.payload.request;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.model.Image;

@Getter
@Setter
public class ItemImageDto {

    private String src;

    public ItemImageDto(Image image) {
        src=image.getLocation();
    }
}
