package shopping.coor.basket.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BasketOrder {
    UP(1),
    DOWN(-1);

    private int number;
}
