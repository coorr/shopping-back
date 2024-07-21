package shopping.coor.domain.item.dto;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.common.page.PageReqDto;

@Getter
@Setter
public class ItemSearchGetReqDto extends PageReqDto {
    private String itemLastId;
    private String category;
}
