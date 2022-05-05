package shopping.coor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @NotBlank
    @Size(max = 20)
    private String dName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String dEmail;

    @NotNull
    private int roadNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String detailText;

    private String message;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP

    public static Delivery createDelivery(String dName, String dEmail, int roadNumber, String address, String detailText, String message) {
        Delivery delivery = new Delivery();
//        delivery.setOrder(order);
        delivery.setDName(dName);
        delivery.setDEmail(dEmail);
        delivery.setRoadNumber(roadNumber);
        delivery.setAddress(address);
        delivery.setDetailText(detailText);
        delivery.setMessage(message);
        delivery.setStatus(DeliveryStatus.READY);
        return delivery;
    }

}
