package shopping.coor.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.coor.domain.user.User;
import shopping.coor.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void init() {
        User.builder()
                .name("kim1")
                .password("123123")
                .email("w@naver.com")
                .build();
    }

    @Test
    public void 회원가입_저장() throws Exception {
        // given
        User user = User.builder()
                .name("kim1")
                .password("123123")
                .email("w@naver.com")
                .build();

        // when
        User save = userRepository.save(user);

        // then
        assertEquals(user.getName(), save.getName());
        assertEquals(user.getPassword(), save.getPassword());
        assertEquals(user.getEmail(), save.getEmail());
    }

    @Test
    public void 사용자_조회() throws Exception {
        // given
        List<User> userList = userList();
        for (User user : userList) {
            userRepository.save(user);
        }

        // when
        List<User> all = userRepository.findAll();

        // then
        assertEquals(all.size(), 2);
    }


    private List<User> userList() {
        List<User> userList = new ArrayList<>();
        Long i = null;
        for (i = 0L; i < 2; i++) {
            userList.add(new User("test"+i, "test@naver.com"+i, "123123"));
        }
        return userList;
    }
}
