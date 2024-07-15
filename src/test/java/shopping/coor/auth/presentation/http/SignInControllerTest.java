package shopping.coor.auth.presentation.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import shopping.coor.domain.user.signin.dto.SignInPostReqDto;
import shopping.coor.config.TestBaseConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignInControllerTest extends TestBaseConfig {

    @Nested
    class SignInCommandTaskTest{

        private SignInPostReqDto request;

        @BeforeEach
        void setUp() {
            request = request.builder().username("user1").password("123123").build();
        }

        @Test
        void 로그인_시도_성공() throws Exception {
            requestSignIn(request).andExpect(status().isOk());
        }

        @Test
        void 로그인_시도_실패() throws Exception {

            request = request.builder().username("test_X").password("123123").build();

            requestSignIn(request).andExpect(status().isBadRequest());
        }

        private ResultActions requestSignIn(SignInPostReqDto request) throws Exception {
            return mockMvc
                    .perform(post("/api/user/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    );
        }
    }

}
