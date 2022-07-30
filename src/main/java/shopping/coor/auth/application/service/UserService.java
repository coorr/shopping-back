package shopping.coor.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.coor.auth.application.exception.UserNotFoundException;
import shopping.coor.auth.domain.User.User;
import shopping.coor.auth.domain.User.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자는 찾을 수 없습니다."));
    }
}
