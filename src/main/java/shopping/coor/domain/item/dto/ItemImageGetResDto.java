package shopping.coor.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.domain.item.image.Image;

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
