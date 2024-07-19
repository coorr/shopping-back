package shopping.coor.common.container;

import org.springframework.validation.Errors;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorsResponse {

    private static final int MAX_ERRORS_COUNT = 20;

    private final List<SimpleErrorResponse> errors;

    private ErrorsResponse(List<SimpleErrorResponse> errors) {
        this.errors = errors.size() > MAX_ERRORS_COUNT
                ? errors.subList(0, MAX_ERRORS_COUNT - 1)
                : errors;
    }

    public static ErrorsResponse create(Errors errors) {

        List<SimpleErrorResponse> errorResponseList = errors.getFieldErrors()
                .stream()
                .map(e -> SimpleErrorResponse.createByError(e))
                .collect(Collectors.toList());

        errorResponseList.addAll(errors.getGlobalErrors()
                .stream()
                .map(e -> SimpleErrorResponse.createByError(e))
                .collect(Collectors.toList()));

        return new ErrorsResponse(errorResponseList);
    }

    public static ErrorsResponse create(String message, String field, String redirectionUrl) {
        return new ErrorsResponse(List.of(new SimpleErrorResponse(message, field, redirectionUrl)));
    }

    public static ErrorsResponse create(String message, String field) {
        return new ErrorsResponse(List.of(new SimpleErrorResponse(message, field)));
    }

    public List<SimpleErrorResponse> getErrors() {
        return errors;
    }
}

