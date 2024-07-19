package shopping.coor.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // To Do : UserDetails -> user 변경 시 loadUserByUsername 수정
    @Query("select u from User u join fetch u.roles where u.name = ?1 ")
    Optional<User> findByEmail(String email);

    @Query("select u from User u join fetch u.roles where u.name = ?1 ")
    User findByNameJoinRoles(String username);


    Boolean existsByName(String username);

    Boolean existsByEmail(String email);

    @Query("select u from User u")
    List<User> selectAll();

    User getById(Long id);
}
