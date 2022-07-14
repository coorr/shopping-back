package shopping.coor.auth.application.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import shopping.coor.auth.application.command.model.CreateUserModel;
import shopping.coor.auth.application.exception.UserAlreadyExistsException;
import shopping.coor.auth.domain.Role.ERole;
import shopping.coor.auth.domain.Role.Role;
import shopping.coor.auth.domain.Role.RoleRepository;
import shopping.coor.auth.domain.User.User;
import shopping.coor.auth.domain.User.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith({MockitoExtension.class})
class CreateUserCommandTest {
    @InjectMocks
    CreateUserCommand createUserCommand;

    @Mock UserRepository userRepository;

    @Mock RoleRepository roleRepository;

    @Mock PasswordEncoder encoder;

    private CreateUserModel model;
    private Role role;
    private User user;

    @BeforeEach
    void setUp() {
        model = CreateUserModel.builder()
                .username("test")
                .email("test@gmail.com")
                .build();
        role = Role.builder().name(ERole.ROLE_USER).build();
        user = user.builder().username("test").email("test@gmail.com").build();

    }

    @Test
    void 로그인_아이디_중복오류() throws Exception {
        given(userRepository.existsByUsername(model.getUsername())).willReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> createUserCommand.execute(model));
    }

    @Test
    void 로그인_이메일_중복오류() throws Exception {
        given(userRepository.existsByEmail(model.getEmail())).willReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> createUserCommand.execute(model));
    }

    @Test
    void 로그인_시도_성공() throws Exception {
        given(userRepository.existsByUsername(model.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(model.getEmail())).willReturn(false);
        given(roleRepository.findByName(any())).willReturn(Optional.ofNullable(role));
        given(userRepository.save(any())).willReturn(user);

        createUserCommand.execute(model);

        then(userRepository).should(atLeastOnce()).existsByEmail(any());
        then(roleRepository).should(atLeastOnce()).findByName(any());
        then(userRepository).should(atLeastOnce()).save(any());
    }


}