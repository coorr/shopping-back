package shopping.coor.domain.user.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.domain.user.signup.dto.SignUpPostReqDto;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;
    private final SignUpPostReqDtoValidator validator;


    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody SignUpPostReqDto dto, BindingResult errors) throws BindException {

        validator.validateTarget(dto, errors);
        this.checkHasError(errors);

        signUpService.signUp(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void checkHasError(BindingResult errors) throws BindException {
        if (errors.hasErrors()) {
            throw new BindException(errors);
        }
    }
}
