package shopping.coor.common.container;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomFieldError {
    private String field;
    private Object value;
    private String reason;
}
