package shopping.coor.order.presentation.http.request;


import lombok.*;
import shopping.coor.order.domain.delivery.Delivery;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderDeliveryCreateReqDto {
    private String name;
    private String email;
    private int roadNumber;
    private String address;
    private String detailText;
    private String message;

    public OrderDeliveryCreateReqDto(OrderDeliveryPostReqDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.roadNumber = dto.getRoadNumber();
        this.address = dto.getAddress();
        this.detailText = dto.getDetailText();
        this.message = dto.getMessage();
    }

    public Delivery toDelivery(OrderDeliveryCreateReqDto dto) {
        return new Delivery(dto);
    }
}
