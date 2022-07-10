package shopping.coor.auth.presentation.http.request;

import lombok.*;

@Getter
@Setter
@Builder
public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }
}
