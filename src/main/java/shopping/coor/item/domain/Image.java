package shopping.coor.item.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import static javax.persistence.FetchType.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @NotBlank
    private String location;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public Image(Long imageId, String location) {
        this.id = imageId;
        this.location = location;
    }

    public static Image createImage(String location, Item itemId) {
        Image image = new Image();
        image.setLocation(location);
        image.setItem(itemId);
        return image;
    }
}
