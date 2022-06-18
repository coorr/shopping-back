package shopping.coor.model;

import lombok.*;
import shopping.coor.common.exception.NotEnoughStockException;

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
public class Item {

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

    public static Item createItem(Item items, Image... images) {
        Item item = new Item();
        item.setTitle(items.getTitle());
        item.setPrice(items.getPrice());
        item.setDiscountPrice(items.getDiscountPrice());
        item.setCategory(items.getCategory());
        item.setSize(items.getSize());
        item.setMaterial(items.getMaterial());
        item.setInfo(items.getInfo());
        for (Image image : images) {
            item.addImage(image);
        }
        return item;
    }

    public Item(Long id, String title, int  discountPrice, int quantityS) {
        this.id = id;
        this.title=title;
        this.discountPrice = discountPrice;
        this.quantityS = quantityS;
    }

}
