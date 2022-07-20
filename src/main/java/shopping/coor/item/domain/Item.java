package shopping.coor.item.domain;

import lombok.*;
import shopping.coor.item.application.exception.NotEnoughStockException;
import shopping.coor.item.presentation.http.request.ItemUpdateReqDto;
import shopping.coor.kernel.domain.BaseEntityAggregateRoot;
import shopping.coor.model.Image;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Builder.Default
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
        image.setItem(this);
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
                throw new NotEnoughStockException("need more stock");
            }
            this.quantityS = restStock;
        }
        if (size.equals("M")) {
            int restStock = this.quantityM - quantity;
            if (restStock < 0) {
                throw new NotEnoughStockException("need more stock");
            }
            this.quantityM = restStock;
        }
        if (size.equals("L")) {
            int restStock = this.quantityL - quantity;
            if (restStock < 0) {
                throw new NotEnoughStockException("need more stock");
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


}

















