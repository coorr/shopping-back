package shopping.coor.payload.request;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.model.Image;

@Getter
@Setter
public class ItemImageDto {

    private Long id;
    private String location;

    public ItemImageDto(Image image) {
        id = image.getId();
        location =image.getLocation();
    }
}
