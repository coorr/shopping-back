package shopping.coor.auth.application.service.body;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateTokenBody {
    private String token;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
}
