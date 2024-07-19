package shopping.coor.domain.user.signup;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import shopping.coor.common.validator.AbstractValidator;
import shopping.coor.domain.user.signup.dto.SignUpPostReqDto;

@Component
public class SignUpPostReqDtoValidator extends AbstractValidator<SignUpPostReqDto> {

    @Override
    public void validateTarget(SignUpPostReqDto request, Errors errors) {
        if (!StringUtils.hasText(request.getUsername())) {
            errors.rejectValue("username", "아이디를 입력해주세요.");
        }
        if (!StringUtils.hasText(request.getPassword())) {
            errors.rejectValue("password", "비밀번호를 입력해주세요.");
        }
    }
}
