package shopping.coor.order.presentation.http.request;

import lombok.*;
import shopping.coor.order.domain.delivery.Delivery;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
