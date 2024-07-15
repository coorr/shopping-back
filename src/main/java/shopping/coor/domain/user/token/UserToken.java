package shopping.coor.domain.user.token;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserToken {
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
