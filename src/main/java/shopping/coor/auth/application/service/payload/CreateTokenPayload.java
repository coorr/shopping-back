package shopping.coor.auth.application.service.payload;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateTokenPayload {
    private String username;
    private String password;
}
