package shopping.coor.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import shopping.coor.domain.user.User;
import shopping.coor.domain.user.UserRepository;
import shopping.coor.domain.user.exception.UserAlreadyExistsException;
import shopping.coor.domain.user.role.ERole;
import shopping.coor.domain.user.role.Role;
import shopping.coor.domain.user.role.RoleRepository;
import shopping.coor.domain.user.signup.SignUpService;
import shopping.coor.domain.user.signup.dto.SignUpPostReqDto;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith({MockitoExtension.class})
class SignUpServiceTest {
    @InjectMocks
    SignUpService signUpService;

    @Mock UserRepository userRepository;

    @Mock RoleRepository roleRepository;

    @Mock PasswordEncoder encoder;

    private SignUpPostReqDto dto;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        dto = SignUpPostReqDto.builder()
                .username("test")
                .email("test@gmail.com")
                .build();
        role = Role.builder().name(ERole.ROLE_USER).build();
        user = user.builder().name("test").email("test@gmail.com").build();

    }

    @Test
    void 로그인_아이디_중복오류() throws Exception {
        given(userRepository.existsByName(dto.getUsername())).willReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> signUpService.signUp(dto));
    }

    @Test
    void 로그인_이메일_중복오류() throws Exception {
        given(userRepository.existsByEmail(dto.getEmail())).willReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> signUpService.signUp(dto));
    }

    @Test
    void 로그인_시도_성공() throws Exception {
        given(userRepository.existsByName(dto.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(dto.getEmail())).willReturn(false);
        given(roleRepository.findByName(any())).willReturn(Optional.ofNullable(role));
        given(userRepository.save(any())).willReturn(user);

        signUpService.signUp(dto);

        then(userRepository).should(atLeastOnce()).existsByEmail(any());
        then(roleRepository).should(atLeastOnce()).findByName(any());
        then(userRepository).should(atLeastOnce()).save(any());
    }


}
