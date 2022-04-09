package shopping.coor.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shopping.coor.model.User;
import shopping.coor.repository.UserRepository;
import shopping.coor.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  {
    private final UserRepository userRepository;

    @Override
    public List<User> selectAll() {
        return userRepository.selectAll();
    }
}
