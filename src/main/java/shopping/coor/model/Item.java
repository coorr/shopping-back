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
    private int discount_price;

    private String category;

    private String size;

    private String material;

    private String info;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<Image> images = new ArrayList<>();
}
