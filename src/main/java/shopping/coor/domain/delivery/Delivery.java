package shopping.coor.domain.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.domain.order.Order;
import shopping.coor.domain.order.dto.OrderDeliveryCreateReqDto;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "road_number")
    private int roadNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "detail_text")
    private String detailText;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    public static Delivery createDelivery(String dName, String dEmail, int roadNumber, String address, String detailText, String message) {
        Delivery delivery = new Delivery();
        delivery.setName(dName);
        delivery.setEmail(dEmail);
        delivery.setRoadNumber(roadNumber);
        delivery.setAddress(address);
        delivery.setDetailText(detailText);
        delivery.setMessage(message);
        delivery.setStatus(DeliveryStatus.READY);
        return delivery;
    }

    public Delivery(OrderDeliveryCreateReqDto dto) {
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.roadNumber = dto.getRoadNumber();
        this.address = dto.getAddress();
        this.detailText = dto.getDetailText();
        this.message = dto.getMessage();
    }

}
