package shopping.coor.item.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import shopping.coor.common.domain.BaseEntityAggregateRoot;
import shopping.coor.item.application.exception.NotEnoughStockException;
import shopping.coor.item.domain.image.Image;
import shopping.coor.item.presentation.http.request.ItemUpdateReqDto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Where(clause = "deleted = false")
public class Item extends BaseEntityAggregateRoot<Item> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private int price;

    @NotNull
    private int discountPrice;

    @NotNull
    private int quantityS;

    @NotNull
    private int quantityM;

    @NotNull
    private int quantityL;

    @NotNull
    private String category;

    @Column(length = 1000)
    private String size;

    @Column(length = 1000)
    private String material;

    @Column(length = 1000)
    private String info;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    private Long updateBy;

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void addImage(List<Image> imageList) {
        for (Image result : imageList) {
            images.add(result);
            result.setItem(this);
        }
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
        this.category = reqDto.getCategory();
        this.size = reqDto.getSize();
        this.material = reqDto.getMaterial();
        this.info = reqDto.getInfo();

        return this;
    }

    public Item delete(LocalDateTime deleteBy) {
        this.setDeleted(true);
        this.deletedAt = deleteBy;

        return this;
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

















