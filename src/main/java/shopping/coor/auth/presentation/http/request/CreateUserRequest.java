package shopping.coor.auth.presentation.http.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일을 입력해주세요.")
    @Size(max = 50)
    private String email;

    private Set<String> role;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 40)
    private String password;
}
