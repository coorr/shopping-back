package shopping.coor.kernel.application.error.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Locale;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleErrorResponse {
    private String message;
    private String field;
    private String redirectionUrl;
    private ResponseResult responseResult = ResponseResult.FAIL;

    public SimpleErrorResponse(String message) {
        this.message = message;
    }

    public SimpleErrorResponse(String message, String field, String redirectionUrl) {
        this.message = message;
        this.field = field;
        this.redirectionUrl = redirectionUrl;
    }

    public static SimpleErrorResponse createByError(ObjectError error, MessageSource messageSource, Locale locale, String redirectionUrl) {
        if (StringUtils.isEmpty(error.getCode()))
            return new SimpleErrorResponse();
        return new SimpleErrorResponse(
                messageSource.getMessage(error.getCode(), null, locale),
                error instanceof FieldError ? ((FieldError) error).getField() : null,
                redirectionUrl);
    }
}
