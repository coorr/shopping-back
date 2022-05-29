package shopping.coor.controller;

import com.google.gson.Gson;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shopping.coor.model.User;
import shopping.coor.repository.user.UserRepository;
import shopping.coor.repository.user.dto.LoginRequest;
import shopping.coor.repository.user.dto.SignupRequest;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void 회원가입_시도() throws Exception {
        // given
        SignupRequest signupRequest = signupRequest();
        ResponseEntity<MessageResponse> responseEntity = ResponseEntity.status(HttpStatus.OK).body(messageResponse());

        when(userService.registerUser(any())).thenReturn(responseEntity);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/signup")
                        .characterEncoding("UTF-8")
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(signupRequest)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("message",messageResponse().getMessage()).exists()).andReturn();

    }

    @Test
    public void 로그인_시도() throws Exception {
        // given
        LoginRequest loginRequest = loginRequest();
        ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).body(loginRequest());

        when(userService.authenticateUser(any(LoginRequest.class))).thenReturn(responseEntity);

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/user/signin")
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(loginRequest)));

        // then
        resultActions.andExpect(status().isOk()).andReturn();


    }

    @Test
    public void 사용자_목록_조회() throws Exception {
        // given
        doReturn(userList()).when(userService).selectAll();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/user/selectAll")
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
    }



    private LoginRequest loginRequest() {
        return LoginRequest.builder()
                .username("title")
                .password("123123")
                .build();
    }

    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        Long i = null;
        for (i = 0L; i < 5; i++) {
            userList.add(new User(i,"test", "test@naver.com", "123123"));
        }
        return userList;
    }

    private SignupRequest signupRequest() {
        return SignupRequest.builder()
                .username("test222")
                .email("W2222@naver.com")
                .password("12312322")
                .build();
    }

    private MessageResponse messageResponse() {
        return MessageResponse.builder()
                .message("회원가입 완료되었습니다.")
                .build();
    }
}