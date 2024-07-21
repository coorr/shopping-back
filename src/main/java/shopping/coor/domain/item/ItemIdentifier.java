package shopping.coor.domain.item;


import lombok.NoArgsConstructor;
import shopping.coor.common.container.LongIdentifier;

@NoArgsConstructor
public class ItemIdentifier extends LongIdentifier {

    public ItemIdentifier(String idStr) {
        super(idStr);
    }
}
