package shopping.coor.auth.application.command.model;

import lombok.Builder;
import lombok.Getter;
import shopping.coor.kernel.application.command.CommandModel;

import java.util.Set;

@Builder
@Getter
public class CreateUserModel implements CommandModel {
    private String username;
    private String email;
    private String password;
    private Set<String> role;

}
