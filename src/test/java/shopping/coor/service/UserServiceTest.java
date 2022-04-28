package shopping.coor.service;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.model.User;
import shopping.coor.payload.request.LoginRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

//    @Autowired
//    protected MockMvc mockMvc;

    @Autowired
    UserService userService;

//    @Test(expected = NullPointerException.class)
//    public void 로그인_예외() throws Exception {
//        LoginRequest loginRequest = new LoginRequest("123", "123");
//        ResponseEntity<?> responseEntity = userService.authenticateUser(loginRequest);
//        System.out.println("responseEntity = " + responseEntity.toString());
//    }

    @Test
    public void 토큰_테스트() throws Exception {




    }

}