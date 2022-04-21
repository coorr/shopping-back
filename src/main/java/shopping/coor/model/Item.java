package shopping.coor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
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

    private String category;

    private String size;

    private String material;

    private String info;

//    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        images.add(image);
        image.setItem(this);
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
}
