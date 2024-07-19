//package shopping.coor.service;
//
//import lombok.Data;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import shopping.coor.domain.user.token.UserTokenService;
//import shopping.coor.auth.application.service.jwt.JwtUtils;
//import shopping.coor.domain.user.role.ERole;
//import shopping.coor.domain.user.role.Role;
//import shopping.coor.domain.user.User;
//import shopping.coor.auth.presentation.http.request.CreateTokenRequest;
//import shopping.coor.auth.presentation.http.request.SignupRequest;
//import shopping.coor.common.MessageResponse;
//import shopping.coor.domain.user.role.RoleRepository;
//import shopping.coor.domain.user.UserRepository;
//import shopping.coor.serviceImpl.user.UserServiceImpl;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceTest {
//
//    @InjectMocks
//    private UserTokenService userService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Mock
//    private JwtUtils jwtUtils;
//
//    @Spy
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//
//    @Test
//    public void 회원가입_시도() throws Exception {
//        // given
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        SignupRequest request = signupRequest();
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role();
//        when(roleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
//        roles.add(role);
//
//        User user = new User(request.getUsername(),
//                request.getEmail(),
//                encoder.encode(request.getPassword()));
//        user.setRoles(roles);
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        // when
//        ResponseEntity<?> responseEntity = userService.createToken(request);
//
//        // then
//        assertEquals(responseEntity.getStatusCodeValue(), 200);
//
//
//    }
//
//    @Test
//    public void 회원가입_동일회원_예외() throws Exception {
//        // given
//        SignupRequest request = signupRequest();
//        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);
//
//        // when
//        ResponseEntity<MessageResponse> responseEntity = userService.registerUser(request);
//
//        // then
//        assertEquals(responseEntity.getStatusCodeValue(), 400);
//        assertEquals(responseEntity.getBody().getMessage(), "이미 존재하는 아이디입니다.");
//    }
//
//    private List<User> userList() {
//        List<User> userList = new ArrayList<>();
//        Long i = null;
//        for (i = 0L; i < 5; i++) {
//            userList.add(new User(i,"test", "test@naver.com", "123123"));
//        }
//        return userList;
//    }
//
//
//    private CreateTokenRequest loginRequest() {
//        return CreateTokenRequest.builder()
//                .username("title")
//                .password("123123")
//                .build();
//    }
//
//    private SignupRequest signupRequest() {
//        return SignupRequest.builder()
//                .username("kim1")
//                .email("W@naver.com")
//                .password("123123")
//                .build();
//    }
//
//
//    @Data
//    static class UserListResponseDTO {
//        private Long id;
//        private String username;
//        private String email;
//        private String password;
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
