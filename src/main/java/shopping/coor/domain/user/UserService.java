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
                .orElseThrow(() -> new UserNotFoundException("해당 사용자는 찾을 수 없습니다."));
    }
}
