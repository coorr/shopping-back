package shopping.coor.domain.item.image.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUpdateReqDto {
    private Long id;
    private String location;
}
