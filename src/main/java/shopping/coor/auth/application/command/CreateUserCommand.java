package shopping.coor.auth.application.command;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.auth.application.command.model.CreateUserModel;
import shopping.coor.auth.application.exception.UserAlreadyExistsException;
import shopping.coor.auth.domain.Role.ERole;
import shopping.coor.auth.domain.Role.Role;
import shopping.coor.auth.domain.Role.RoleRepository;
import shopping.coor.auth.domain.User.User;
import shopping.coor.auth.domain.User.UserRepository;
import shopping.coor.kernel.application.command.Command;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserCommand implements Command<CreateUserModel> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void execute(CreateUserModel model) {
        if (userRepository.existsByUsername(model.getUsername())) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(model.getEmail())) {
            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        User user = new User(model.getUsername(),
                model.getEmail(),
                encoder.encode(model.getPassword()));

        Set<String> strRoles = model.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("권한 오류입니다."));

            roles.add(userRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }
}
