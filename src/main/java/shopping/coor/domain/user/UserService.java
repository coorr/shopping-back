package shopping.coor.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.coor.domain.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}
