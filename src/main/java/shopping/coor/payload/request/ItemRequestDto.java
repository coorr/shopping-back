package shopping.coor.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    public String title;
    public int price;
    public int discount_price;
    public String category;
    public String size;
    public String material;
    public String info;
}
