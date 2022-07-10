package shopping.coor.auth.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import shopping.coor.auth.application.service.jwt.JwtUtils;
import shopping.coor.auth.application.service.payload.CreateTokenPayload;
import shopping.coor.auth.domain.User.User;

@ExtendWith({MockitoExtension.class})
class UserTokenServiceTest {
    @InjectMocks
    UserTokenService service;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtUtils jwtUtils;

    @Mock
    User user;


    @Nested
    class CreateTokenTest {
        private CreateTokenPayload payload;
        private Authentication authentication;

        @BeforeEach
        void setUp() {
            payload = CreateTokenPayload.builder().username("test@gmail.com").password("password").build();

        }

        @Test
        @WithAnonymousUser
        @DisplayName("회원이 있고 비밀번호가 맞다면 토큰을 발급할 수 있어야 한다.")
        void should_be_able_to_create_token() {
////            authenticationManager.authenticate(
////                    new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
//            given(authenticationManager.authenticate(any())).willReturn(mock(Authentication.class));
//            given(jwtUtils.generateJwtToken(any())).willReturn("22");
////            given(authentication.getPrincipal()).willReturn(UserDetailsImpl.build(user));
//            var result = service.createToken(payload);
//            System.out.println("result = " + result);
        }
    }
}