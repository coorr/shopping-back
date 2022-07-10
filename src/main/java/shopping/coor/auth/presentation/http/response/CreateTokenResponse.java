package shopping.coor.auth.presentation.http.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateTokenResponse {
	private String token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles;

}
