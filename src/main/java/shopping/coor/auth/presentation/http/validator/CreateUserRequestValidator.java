package shopping.coor.auth.presentation.http.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import shopping.coor.auth.presentation.http.request.CreateUserRequest;
import shopping.coor.common.presentation.request.AbstractValidator;

@Component
public class CreateUserRequestValidator extends AbstractValidator<CreateUserRequest> {

    @Override
    public void validateTarget(CreateUserRequest request, Errors errors) {
        if (!StringUtils.hasText(request.getUsername())) {
            errors.rejectValue("username", "아이디를 입력해주세요.");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            errors.rejectValue("password", "비밀번호를 입력해주세요.");
        }
    }
}
