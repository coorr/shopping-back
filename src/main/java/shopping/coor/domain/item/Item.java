package shopping.coor.domain.item;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.util.ObjectUtils;
import shopping.coor.common.model.BaseEntityCreateUpdateAggregate;
import shopping.coor.domain.item.dto.ItemUpdateReqDto;
import shopping.coor.domain.item.enums.ItemCategory;
import shopping.coor.domain.item.exception.NotEnoughStockException;
import shopping.coor.domain.item.image.Image;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Where(clause = "deleted = false")
public class Item extends BaseEntityCreateUpdateAggregate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "discount_price", nullable = false)
    private int discountPrice;

    @Column(name = "quantityS", nullable = false)
    private int quantityS;

    @Column(name = "quantityM", nullable = false)
    private int quantityM;

    @Column(name = "quantityL", nullable = false)
    private int quantityL;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ItemCategory category;

    @Column(name = "size_info")
    private String sizeInfo;

    @Column(name = "material")
    private String material;

    @Column(name = "info")
    private String info;

    @Column(name = "deleted")
    private boolean deleted = false;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public Item(String title, int price, int discountPrice, int quantityS, int quantityM, int quantityL,
                ItemCategory category, String sizeInfo, String material, String info) {
        this.title = title;
        this.price = price;
        this.discountPrice = discountPrice;
        this.quantityS = quantityS;
        this.quantityM = quantityM;
        this.quantityL = quantityL;
        this.category = category;
        this.sizeInfo = sizeInfo;
        this.material = material;
        this.info = info;
    }

    public void addImage(List<Image> images) {
        for (Image image : images) {
            image.setItem(this);
        }
        this.images = images;
    }

    public void setItem(List<Image> images) {
        if (ObjectUtils.isEmpty(images)) {
            return;
        }
        this.images = images;
    }

    public Item(Long id, String title, int  discountPrice, int quantityS) {
        this.id = id;
        this.title=title;
        this.discountPrice = discountPrice;
        this.quantityS = quantityS;
    }

    public Item update(ItemUpdateReqDto reqDto) {
        this.title = reqDto.getTitle();
        this.price = reqDto.getPrice();
        this.discountPrice = reqDto.getDiscountPrice();
        this.quantityS = reqDto.getQuantityS();
        this.quantityM = reqDto.getQuantityM();
        this.quantityL = reqDto.getQuantityL();
        this.category = ItemCategory.valueOf(reqDto.getCategory());
        this.sizeInfo = reqDto.getSize();
        this.material = reqDto.getMaterial();
        this.info = reqDto.getInfo();

        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public void addStock(int quantity, String size) {
        if (size.equals("S")) {
            this.quantityS += quantity;
        }
        if (size.equals("M")) {
            this.quantityM += quantity;
        }
        if (size.equals("L")) {
            this.quantityL += quantity;
        }
    }

    public void removeStock(int quantity, String size)  {
        if (size.equals("S")) {
            int restStock = getRestStock(this.quantityS, quantity, this.title);
            this.quantityS = restStock;
        }
        if (size.equals("M")) {
            int restStock = getRestStock(this.quantityM, quantity, this.title);
            this.quantityM = restStock;
        }
        if (size.equals("L")) {
            int restStock = getRestStock(this.quantityL, quantity, this.title);
            this.quantityL = restStock;
        }
    }

    // 아이템 수량 확인만 하는 코드
    public void stockCheck(int quantity, String size) {
        switch (size) {
            case "S":
                getRestStock(this.quantityS, quantity, this.title);
                break;
            case "M":
                getRestStock(this.quantityM, quantity, this.title);
                break;
            case "L":
                getRestStock(this.quantityL, quantity, this.title);
                break;
            default:
                break;
        }
    }

    private int getRestStock(int quantityCurrent, int quantityNext, String title) {
        int restStock = quantityCurrent - quantityNext;
        if (restStock < 0) {
            throw new NotEnoughStockException(title);
        }
        return restStock;
    }
}

















