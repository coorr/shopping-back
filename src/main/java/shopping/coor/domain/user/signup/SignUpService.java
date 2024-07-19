package shopping.coor.domain.user.signup;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.domain.user.User;
import shopping.coor.domain.user.UserRepository;
import shopping.coor.domain.user.exception.UserAlreadyExistsException;
import shopping.coor.domain.user.role.ERole;
import shopping.coor.domain.user.role.Role;
import shopping.coor.domain.user.role.RoleRepository;
import shopping.coor.domain.user.signup.dto.SignUpPostReqDto;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void signUp(SignUpPostReqDto dto) {
        if (userRepository.existsByName(dto.getUsername())) {
            throw new UserAlreadyExistsException("이미 존재하는 아이디입니다.");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        User user = new User(dto.getUsername(),
                dto.getEmail(),
                encoder.encode(dto.getPassword()));

        Set<String> strRoles = dto.getRole();
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
