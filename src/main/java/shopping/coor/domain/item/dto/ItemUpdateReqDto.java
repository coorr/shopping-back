package shopping.coor.domain.item.dto;

import lombok.*;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ItemUpdateReqDto {
    private String title;
    private int price;
    private int discountPrice;
    private int quantityS;
    private int quantityM;
    private int quantityL;
    private String category;
    private String size;
    private String material;
    private String info;
    private List<ImageUpdateReqDto> imagePath;

    public ItemUpdateReqDto(ItemPatchUpdateReqDto reqDto) {
        this.title = reqDto.getTitle();
        this.price = reqDto.getPrice();
        this.discountPrice = reqDto.getDiscountPrice();
        this.quantityS = reqDto.getQuantityS();
        this.quantityM = reqDto.getQuantityM();
        this.quantityL = reqDto.getQuantityL();
        this.category = reqDto.getCategory();
        this.size = reqDto.getSize();
        this.material = reqDto.getMaterial();
        this.info = reqDto.getInfo();
        this.imagePath = reqDto.getImagePath();
    }


}
