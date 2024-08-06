package shopping.coor.domain.item.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.domain.item.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "location")
    private String location;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "i_id")
    private Item item;

    public Image(String location, Item item) {
        this.item = item;
        this.location = location;
    }

    public static Image createImage(String location, Item itemsId) {
        Image image = new Image();
        image.setLocation(location);
        image.setItem(itemsId);
        return image;
    }
}
