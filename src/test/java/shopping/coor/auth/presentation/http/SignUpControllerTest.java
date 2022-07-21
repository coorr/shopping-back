package shopping.coor.auth.presentation.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import shopping.coor.auth.presentation.http.request.CreateUserRequest;
import shopping.coor.config.TestBaseConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignUpControllerTest extends TestBaseConfig {


    @Nested
    class SignUpCommandTaskTest {

        private CreateUserRequest request;

        @BeforeEach
        void setUp() {
            request = request.builder()
                    .username("test12322")
                    .password("123123")
                    .email("tes2t2@gmail.com")
                    .build();
        }

        @Test
        void 회원가입_시도_성공() throws Exception {
            requestSignup(request).andExpect(status().isCreated());
        }

        @Test
        void 회원가입_아이디_NULL_오류() throws Exception {
            request = request.builder().username(null).password("123123").email("test9@gmail.com").build();

            requestSignup(request).andExpect(status().isBadRequest());
        }

        @Test
        void 회원가입_아이디_빈칸_오류() throws Exception {
            request = request.builder().username("").password("123123").email("test9@gmail.com").build();

            requestSignup(request).andExpect(status().isBadRequest());
        }

        private ResultActions requestSignup(CreateUserRequest request) throws Exception {
            return mockMvc.perform(
                    post("/api/user/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );
        }

    }

}