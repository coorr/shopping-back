package shopping.coor.kernel.application.error.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomFieldError {
    private String field;
    private Object value;
    private String reason;
}
