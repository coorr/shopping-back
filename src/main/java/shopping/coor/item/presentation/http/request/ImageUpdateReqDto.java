package shopping.coor.item.presentation.http.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageUpdateReqDto {
    private Long id;
    private String location;
}
