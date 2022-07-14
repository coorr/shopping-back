package shopping.coor.kernel.application.error.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorsResponse {
    private List<SimpleErrorResponse> errors;

    public static ErrorsResponse create(Errors errors) {

        List<SimpleErrorResponse> errorResponseList = errors.getFieldErrors()
                .stream()
                .map(e -> new SimpleErrorResponse(e.getDefaultMessage(), e.getField(), "/cw/member/login.do"))
                .collect(Collectors.toList());

        errorResponseList.addAll(errors.getGlobalErrors()
                .stream()
                .map(e -> new SimpleErrorResponse(e.getDefaultMessage()))
                .collect(Collectors.toList()));

        return new ErrorsResponse(errorResponseList);
    }

    public static ErrorsResponse create(Errors errors, MessageSource messageSource, Locale locale, String redirectionUrl) {

        List<SimpleErrorResponse> errorResponseList = errors.getFieldErrors()
                .stream()
                .map(e -> SimpleErrorResponse.createByError(e, messageSource, locale, redirectionUrl))
                .collect(Collectors.toList());

        errorResponseList.addAll(errors.getGlobalErrors()
                .stream()
                .map(e -> SimpleErrorResponse.createByError(e, messageSource, locale, redirectionUrl))
                .collect(Collectors.toList()));

        return new ErrorsResponse(errorResponseList);
    }

    public static ErrorsResponse create(ObjectError objectError, MessageSource messageSource, Locale locale, String redirectionUrl) {
        List<SimpleErrorResponse> errorResponseList = new ArrayList<>();
        errorResponseList.add(SimpleErrorResponse.createByError(objectError, messageSource, locale, redirectionUrl));
        return new ErrorsResponse(errorResponseList);
    }

    public static ErrorsResponse create(String message, String field, String redirectionUrl) {
        return new ErrorsResponse(List.of(new SimpleErrorResponse(message, field, redirectionUrl)));
    }

    public static ErrorsResponse create(String message) {
        return new ErrorsResponse(List.of(new SimpleErrorResponse(message)));
    }
}
