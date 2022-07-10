package shopping.coor.auth.presentation.http.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CreateTokenRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;


}
