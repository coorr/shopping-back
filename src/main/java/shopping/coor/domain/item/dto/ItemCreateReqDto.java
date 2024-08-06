package shopping.coor.domain.item.dto;

import lombok.*;
import shopping.coor.domain.item.Item;
import shopping.coor.domain.item.enums.ItemCategory;

@Getter
public class ItemCreateReqDto {
    private String title;

    private int price;

    private int discountPrice;

    private int quantityS;

    private int quantityM;

    private int quantityL;

    private ItemCategory category;

    private String sizeInfo;

    private String material;

    private String info;

    public ItemCreateReqDto(ItemPostReqDto dto) {
        this.title = dto.getTitle();
        this.price = dto.getPrice();
        this.discountPrice = dto.getDiscountPrice();
        this.quantityS = dto.getQuantityS();
        this.quantityM = dto.getQuantityM();
        this.quantityL = dto.getQuantityL();
        this.category = ItemCategory.valueOf(dto.getCategory());
        this.sizeInfo = dto.getSize();
        this.material = dto.getMaterial();
        this.info = dto.getInfo();
    }

    public Item toEntity() {
        return new Item(this.title, this.price, this.discountPrice, this.quantityS, this.quantityM,
                this.quantityL, this.category, this.sizeInfo, this.material, this.info);
    }
}
