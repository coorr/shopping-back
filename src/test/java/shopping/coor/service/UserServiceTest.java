package shopping.coor.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import shopping.coor.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @Test
    public void 그냥테스트() throws Exception {
        List<User> userList = userService.selectAll();
        for (User user : userList) {
            System.out.println(user.getEmail());
        }



    }

}