package shopping.coor.service;

import io.sentry.protocol.Message;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shopping.coor.model.ERole;
import shopping.coor.model.Role;
import shopping.coor.model.User;
import shopping.coor.payload.request.SignupRequest;
import shopping.coor.payload.response.MessageResponse;
import shopping.coor.repository.RoleRepository;
import shopping.coor.repository.UserRepository;
import shopping.coor.serviceImpl.user.UserServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


//@SpringBootTest
//@Transactional
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    
    @Test
    public void 회원가입() throws Exception {
        // given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        SignupRequest request = signupRequest();

        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        roles.add(role);
        User user = new User(request.getUsername(),
                request.getEmail(),
                encoder.encode(request.getPassword()));
        user.setRoles(roles);
        doReturn(user).when(userRepository).save(any(User.class));

        // when
        ResponseEntity<?> responseEntity = userService.registerUser(request);

        // then
        assertEquals(responseEntity.getStatusCodeValue(), 200);


    }



    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            userList.add(new User("test", "test@navrc.om", "123123"));
        }
        return userList;
    }

    @Data
    static class UserListResponseDTO  {
        private Long id;
        private String username;
        private String email;
        private String password;


    }


    private SignupRequest signupRequest() {


        return SignupRequest.builder()
                .username("test")
                .email("W@naver.com")
                .password("123123")
                .build();
    }

    private MessageResponse messageResponse() {
        return MessageResponse.builder()
                .message("회원가입 완료되었습니다.")
                .build();
    }
}















