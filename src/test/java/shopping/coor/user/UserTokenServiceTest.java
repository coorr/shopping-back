package shopping.coor.user;

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
import shopping.coor.domain.user.signin.JwtService;
import shopping.coor.domain.user.token.UserTokenReqDto;
import shopping.coor.domain.user.User;
import shopping.coor.domain.user.token.UserTokenService;

@ExtendWith({MockitoExtension.class})
class UserTokenServiceTest {
    @InjectMocks
    UserTokenService service;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @Mock
    User user;


    @Nested
    class CreateTokenTest {
        private UserTokenReqDto payload;
        private Authentication authentication;

        @BeforeEach
        void setUp() {
            payload = UserTokenReqDto.builder().username("test@gmail.com").password("password").build();

        }

        @Test
        @WithAnonymousUser
        @DisplayName("회원이 있고 비밀번호가 맞다면 토큰을 발급할 수 있어야 한다.")
        void should_be_able_to_create_token() {

        }
    }
}
