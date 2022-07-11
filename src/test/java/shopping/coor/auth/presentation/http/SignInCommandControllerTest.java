package shopping.coor.auth.presentation.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shopping.coor.auth.presentation.http.request.CreateTokenRequest;
import shopping.coor.config.TestBaseConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignInCommandControllerTest extends TestBaseConfig {

    @Nested
    class SignInCommandTaskTest{

        private CreateTokenRequest request;

        @BeforeEach
        void setUp() {
            request = request.builder().username("user1").password("123123").build();
        }

        @Test
        @DisplayName("로그인 시도 성공")
        void should_be_login_success() throws Exception {
            mockMvc
                    .perform(post("/api/user/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("로그인 시도 실패")
        void should_be_login_failure() throws Exception {

            request = request.builder().username("test_X").password("123123").build();

            mockMvc
                    .perform(post("/api/user/signin")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                    .andExpect(status().isBadRequest());
        }
    }

}