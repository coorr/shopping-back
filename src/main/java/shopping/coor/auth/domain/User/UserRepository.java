package shopping.coor.auth.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // To Do : UserDetails -> user 변경 시 loadUserByUsername 수정
    @Query("select u from User u join fetch u.roles where u.username = ?1 ")
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("select u from User u join fetch u.roles where u.username = ?1 ")
    User findByUsernameJoinRoles(String username);


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("select u from User u")
    List<User> selectAll();

    User getById(Long id);
}
