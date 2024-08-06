package shopping.coor.domain.item.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUpdateReqDto {
    private Long id;
    private String location;
}
