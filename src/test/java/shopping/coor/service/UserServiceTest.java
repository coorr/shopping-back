package shopping.coor.service;

import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shopping.coor.jwt.JwtUtils;
import shopping.coor.model.ERole;
import shopping.coor.model.Role;
import shopping.coor.model.User;
import shopping.coor.repository.user.dto.LoginRequest;
import shopping.coor.repository.user.dto.SignupRequest;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.repository.user.RoleRepository;
import shopping.coor.repository.user.UserRepository;
import shopping.coor.serviceImpl.user.UserServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;


    @Test
    public void 회원가입_시도() throws Exception {
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

        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        ResponseEntity<?> responseEntity = userService.registerUser(request);

        // then
        assertEquals(responseEntity.getStatusCodeValue(), 200);


    }


    @BeforeEach
    void init() {
        User.builder()
                .username("kim1")
                .email("W@naver.com")
                .password("123123")
                .build();
    }

    @Test
    public void 회원가입_동일회원_예외() throws Exception {
        SignupRequest request = signupRequest();
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);


        ResponseEntity<MessageResponse> responseEntity = userService.registerUser(request);

        assertEquals(responseEntity.getStatusCodeValue(), 400);
        assertEquals(responseEntity.getBody().getMessage(), "이미 존재하는 아이디입니다.");
    }

    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        Long i = null;
        for (i = 0L; i < 5; i++) {
            userList.add(new User(i,"test", "test@naver.com", "123123"));
        }
        return userList;
    }


    private LoginRequest loginRequest() {
        return LoginRequest.builder()
                .username("title")
                .password("123123")
                .build();
    }

    private SignupRequest signupRequest() {
        return SignupRequest.builder()
                .username("kim1")
                .email("W@naver.com")
                .password("123123")
                .build();
    }


    @Data
    static class UserListResponseDTO {
        private Long id;
        private String username;
        private String email;
        private String password;
    }


}















