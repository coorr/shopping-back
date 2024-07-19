package shopping.coor.common.container;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Getter
@AllArgsConstructor
public class SimpleErrorResponse {
    private final String message;
    private final String field;
    private final String redirectionUrl;

    public SimpleErrorResponse() {
        this(StringUtils.EMPTY);
    }

    public SimpleErrorResponse(String message) {
        this(message, null);
    }

    public SimpleErrorResponse(String message, String field) {
        this(message, field, null);
    }

    public static SimpleErrorResponse createByError(ObjectError error) {
        if (StringUtils.isBlank(error.getDefaultMessage()))
            return new SimpleErrorResponse();

        String messageRow = generateRowNum(error);

        return new SimpleErrorResponse(
                messageRow.concat(error.getDefaultMessage()),
                error instanceof FieldError ? ((FieldError) error).getField() : null);
    }

    private static String generateRowNum(ObjectError error) {
        if (error.getArguments() != null && error.getArguments().length == 1) {
            try {
                int rowNum = Integer.parseInt(error.getArguments()[0].toString());
                return Integer.toString(rowNum);
            } catch (Exception ignored) {}

        }
        return "";
    }
}
