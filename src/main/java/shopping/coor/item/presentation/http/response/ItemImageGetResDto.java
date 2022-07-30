package shopping.coor.item.presentation.http.response;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.item.domain.Image;

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
