package shopping.coor.auth.presentation.http.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shopping.coor.auth.presentation.http.request.CreateUserRequest;
import shopping.coor.kernel.presentation.request.AbstractValidator;

@Component
@RequiredArgsConstructor
public class CreateUserRequestValidator extends AbstractValidator<CreateUserRequest> {

    @Override
    public void validateTarget(CreateUserRequest request, Errors errors) {
        System.out.println(request.getUsername());
        if (request.getUsername().isEmpty()) {
            errors.rejectValue("username", "username not empty","이름 데이터가 없습니다.");
        }
    }
}
