package shopping.coor.payload.request;

import lombok.Getter;
import lombok.Setter;
import shopping.coor.model.Item;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

//private Long id;
//
//@NotBlank
//private String title;
//
//@NotNull
//private int price;
//
//@NotNull
//private int discountPrice;
//
//private String category;
//
//private String size;
//
//private String material;
//
//private String info;
//
////    @JsonIgnore
//@OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
//private List<Image> images = new ArrayList<>();
@Getter
@Setter
public class ItemRequestOneDto {

    private Long itemId;
    private String title;
    private int price;
    private int discount_price;
    private String category;
    private String size;
    private String material;
    private String info;
    private List<ItemImageDto> images;

    public ItemRequestOneDto(Item item) {
        itemId = item.getId();
        title = item.getTitle();
        price = item.getPrice();
        discount_price = item.getDiscountPrice();
        category = item.getCategory();
        size = item.getSize();
        material = item.getMaterial();
        info = item.getInfo();
        images = item.getImages().stream()
                .map(image -> new ItemImageDto(image))
                .collect(Collectors.toList());
    }


}
