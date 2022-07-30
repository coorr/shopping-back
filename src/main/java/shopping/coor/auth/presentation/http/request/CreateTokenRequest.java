package shopping.coor.auth.presentation.http.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateTokenRequest {
	@NotBlank(message = "아이디를 입력해주세요.")
	private String username;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;


}
