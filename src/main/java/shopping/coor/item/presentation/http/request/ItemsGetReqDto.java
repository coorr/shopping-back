package shopping.coor.item.presentation.http.request;

import lombok.Builder;
import lombok.Getter;
import shopping.coor.item.presentation.http.response.ItemImageGetResDto;

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
