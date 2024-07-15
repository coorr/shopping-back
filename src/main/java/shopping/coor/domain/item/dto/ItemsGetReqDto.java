package shopping.coor.domain.item.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ItemsGetReqDto {
    private Long itemId;
    private String title;
    private int price;
    private int discount_price;
    private List<ItemImageGetResDto> images;




}
