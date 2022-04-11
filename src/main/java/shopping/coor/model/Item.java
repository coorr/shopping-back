package shopping.coor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    public Long id;

    @NotBlank
    public String title;

    @NotNull
    public int price;

    @NotNull
    public int discount_price;

    public String category;

    public String size;

    public String material;

    public String info;
}
