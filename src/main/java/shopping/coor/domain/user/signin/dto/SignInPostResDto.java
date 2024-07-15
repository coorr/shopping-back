package shopping.coor.domain.user.signin.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SignInPostResDto {
	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

}
