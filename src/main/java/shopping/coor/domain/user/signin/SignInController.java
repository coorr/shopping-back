package shopping.coor.domain.user.signin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.domain.user.token.UserTokenService;
import shopping.coor.domain.user.token.UserTokenReqDto;
import shopping.coor.domain.user.signin.dto.SignInPostReqDto;
import shopping.coor.domain.user.signin.dto.SignInPostResDto;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/user", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class SignInController {

    private final UserTokenService service;

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@Valid @RequestBody SignInPostReqDto dto) {
        var result =
                service.createToken(
                    UserTokenReqDto.builder()
                            .username(dto.getUsername())
                            .password(dto.getPassword())
                            .build());

        return ResponseEntity.ok(
                SignInPostResDto.builder()
                        .id(result.getId())
                        .roles(result.getRoles())
                        .email(result.getEmail())
                        .token(result.getToken())
                        .username(result.getUsername())
                        .build()
        );
    }
}
