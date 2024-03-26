package shopping.coor.auth.presentation.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.auth.application.service.UserTokenService;
import shopping.coor.auth.application.service.payload.CreateTokenPayload;
import shopping.coor.auth.presentation.http.request.CreateTokenRequest;
import shopping.coor.auth.presentation.http.response.CreateTokenResponse;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class SignInController {

    private final UserTokenService service;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody CreateTokenRequest request) {
        var result =
                service.createToken(
                    CreateTokenPayload.builder()
                            .username(request.getUsername())
                            .password(request.getPassword())
                            .build());

        return ResponseEntity.ok(
                CreateTokenResponse.builder()
                        .id(result.getId())
                        .roles(result.getRoles())
                        .email(result.getEmail())
                        .token(result.getToken())
                        .username(result.getUsername())
                        .build()
        );
    }
}
