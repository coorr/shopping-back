package shopping.coor.auth.presentation.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import shopping.coor.auth.presentation.http.request.CreateUserRequest;
import shopping.coor.config.TestBaseConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignUpCommandControllerTest extends TestBaseConfig {


    @Nested
    class SignUpCommandTaskTest {

        private CreateUserRequest request;

        @BeforeEach
        void setUp() {
            request = request.builder()
                    .username("test123")
                    .password("123123")
                    .email("test@gmail.com")
                    .build();
        }

        @Test
        void 회원가입_시도_성공() throws Exception {
            mockMvc
                    .perform(post("/api/user/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }

        @Test
        void 회원가입_아이디_빈칸_오류() throws Exception {

            request = request.builder().username(null).password("123123").email("test@gmail.com").build();

            mockMvc
                    .perform(post("/api/user/signup")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)));
        }

    }

}