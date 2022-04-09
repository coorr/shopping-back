package shopping.coor.service;

import org.springframework.stereotype.Service;
import shopping.coor.model.User;

import java.util.List;


public interface UserService {
    public List<User> selectAll();
}
