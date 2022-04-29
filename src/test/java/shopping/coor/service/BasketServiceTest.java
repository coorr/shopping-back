package shopping.coor.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.jwt.JwtUtils;
import shopping.coor.model.Basket;
import shopping.coor.model.User;
import shopping.coor.payload.request.LoginRequest;
import shopping.coor.repository.BasketRepository;
import shopping.coor.repository.UserRepository;
import shopping.coor.serviceImpl.BasketServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
class BasketServiceTest {

    @InjectMocks
    BasketServiceImpl basketService;
    
    @Mock
    BasketRepository basketRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtUtils jwtUtils;

    @Spy
    AuthenticationManager authenticationManager;

    @Test
    public void test2() throws Exception {
        Long userId = 1L;

        User byId = doReturn(userList()).when(userRepository).getById(userId);


    }

    private LoginRequest loginRequest() {
        return LoginRequest.builder()
                .username("test")
                .password("123123")
                .build();
    }

    @Test
    public void test() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@naver.com");
        user.setPassword("123123");

    }

    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        Long i = null;
        for (i = 0L; i < 5; i++) {
            userList.add(new User(i,"test", "test@naver.com", "123123"));
        }
        return userList;
    }

    private List<Basket> basketList() {
        List<Basket> basketList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            basketList.add(new Basket(userList().get(1), 30000));
        }
        return basketList;
    }
}