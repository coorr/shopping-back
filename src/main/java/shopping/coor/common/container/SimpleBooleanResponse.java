package shopping.coor.common.container;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SimpleBooleanResponse {
    private Boolean result;

}
