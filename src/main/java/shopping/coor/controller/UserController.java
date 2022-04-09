package shopping.coor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.coor.model.User;
import shopping.coor.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("selectAll")
    public List<User> selectAll() {
        return userService.selectAll();
    }
}
