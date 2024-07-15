package shopping.coor.domain.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUpdateReqDto {
    private Long id;
    private String location;
}
