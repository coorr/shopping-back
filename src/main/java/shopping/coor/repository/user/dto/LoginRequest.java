package shopping.coor.repository.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;


}
