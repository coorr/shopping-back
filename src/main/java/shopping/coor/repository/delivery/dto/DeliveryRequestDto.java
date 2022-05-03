package shopping.coor.repository.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shopping.coor.model.Delivery;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRequestDto {

    private String name;
    private String email;
    private int roadNumber;
    private String address;
    private String detailText;
    private String message;

    public DeliveryRequestDto(Delivery delivery) {
        this.name = delivery.getDName();
        this.email = delivery.getDEmail();
        this.roadNumber = delivery.getRoadNumber();
        this.address = delivery.getAddress();
        this.detailText = delivery.getDetailText();
        this.message = delivery.getMessage();
    }
}
