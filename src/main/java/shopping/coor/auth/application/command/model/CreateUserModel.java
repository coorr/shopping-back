package shopping.coor.auth.application.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shopping.coor.auth.presentation.http.request.CreateUserRequest;
import shopping.coor.kernel.application.command.CommandModel;

import java.util.Set;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserModel implements CommandModel {
    private String username;
    private String email;
    private String password;
    private Set<String> role;

    public CreateUserModel(CreateUserRequest request) {
        this.username = request.getUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
    }
}
