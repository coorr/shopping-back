package shopping.coor.item.domain;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import shopping.coor.basket.presentation.http.request.BasketPostReqDto;
import shopping.coor.item.application.exception.NotEnoughStockException;
import shopping.coor.item.presentation.http.request.ItemUpdateReqDto;
import shopping.coor.kernel.domain.BaseEntityAggregateRoot;

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

    public void addStock(int quantity, String orderSize) {
        if (orderSize.equals("S")) {
            this.quantityS += quantity;
        }
        if (orderSize.equals("M")) {
            this.quantityM += quantity;
        }
        if (orderSize.equals("L")) {
            this.quantityL += quantity;
        }
    }

    public void removeStock(int quantity, String size)  {
        if (size.equals("S")) {
            int restStock = this.quantityS - quantity;
            if (restStock < 0) {
                throw new NotEnoughStockException();
            }
            this.quantityS = restStock;
        }
        if (size.equals("M")) {
            int restStock = this.quantityM - quantity;
            if (restStock < 0) {
                throw new NotEnoughStockException();
            }
            this.quantityM = restStock;
        }
        if (size.equals("L")) {
            int restStock = this.quantityL - quantity;
            if (restStock < 0) {
                throw new NotEnoughStockException();
            }
            this.quantityL = restStock;
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


    public Item postCheck(BasketPostReqDto basketDto) {
        if (basketDto.getSize().equals("S")) {
            int restStock = this.quantityS - basketDto.getItemCount();
            if (restStock < 0) {
                throw new NotEnoughStockException(basketDto.getTitle());
            }
        }
        if (basketDto.getSize().equals("M")) {
            int restStock = this.quantityM - basketDto.getItemCount();
            if (restStock < 0) {
                throw new NotEnoughStockException(basketDto.getTitle());
            }
            this.quantityM = restStock;
        }
        if (basketDto.getSize().equals("L")) {
            int restStock = this.quantityL - basketDto.getItemCount();
            if (restStock < 0) {
                throw new NotEnoughStockException(basketDto.getTitle());
            }
            this.quantityL = restStock;
        }
        return this;
    }
}

















