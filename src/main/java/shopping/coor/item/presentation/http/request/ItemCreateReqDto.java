package shopping.coor.item.presentation.http.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping.coor.item.domain.Item;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCreateReqDto {
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

    public ItemCreateReqDto(ItemPostCreateReqDto reqDto) {
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
    }

    public Item toEntity() {
        return Item.builder()
                .title(this.getTitle())
                .price(this.getPrice())
                .discountPrice(this.getDiscountPrice())
                .quantityS(this.getQuantityS())
                .quantityM(this.getQuantityM())
                .quantityL(this.getQuantityL())
                .category(this.getCategory())
                .size(this.getSize())
                .material(this.getMaterial())
                .info(this.getInfo())
                .build();
    }
}
