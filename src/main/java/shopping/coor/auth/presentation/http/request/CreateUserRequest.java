package shopping.coor.auth.presentation.http.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 3, max = 20, message = "아이디 3~20 사이여야합니다.")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일을 입력해주세요.")
    @Size(max = 50, message = "이메일 50자 이합니다.")
    private String email;

    private Set<String> role;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 40, message = "비밀번호 6~40 사이여야 합니다.")
    private String password;
}
