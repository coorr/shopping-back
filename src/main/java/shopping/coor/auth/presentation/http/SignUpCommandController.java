package shopping.coor.auth.presentation.http;

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
import shopping.coor.auth.application.command.CreateUserCommand;
import shopping.coor.auth.application.command.model.CreateUserModel;
import shopping.coor.auth.presentation.http.request.CreateUserRequest;
import shopping.coor.auth.presentation.http.validator.CreateUserRequestValidator;
import shopping.coor.kernel.application.command.CommandExecutor;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class SignUpCommandController {

    private final CreateUserCommand createUserCommand;
    private final CreateUserRequestValidator validator;


    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserRequest request, BindingResult errors) throws BindException {

        validator.validateTarget(request, errors);
        this.checkHasError(errors);

        var command = new CommandExecutor<>(
                createUserCommand,
                new CreateUserModel(request));

        command.invoke();

        return ResponseEntity.status(HttpStatus.CREATED).build();


    }

    private void checkHasError(BindingResult errors) throws BindException {
        if (errors.hasErrors()) {
            throw new BindException(errors);
        }
    }
}
