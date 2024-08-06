package shopping.coor.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.coor.domain.user.exception.UserNotFoundException;
import shopping.coor.domain.user.role.ERole;
import shopping.coor.domain.user.role.Role;
import shopping.coor.domain.user.signin.exception.CustomAccessDeniedException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserRepository userRepository;

    public void verifyAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Set<Role> roles = user.getRoles();
        boolean isAuth = false;
        for (Role role : roles) {
            if (ERole.ROLE_ADMIN.equals(role.getName())) {
                isAuth = true;
                break;
            }
        }

        if (!isAuth) {
            throw new CustomAccessDeniedException();
        }
    }
}
