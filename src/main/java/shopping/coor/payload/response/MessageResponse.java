package shopping.coor.payload.response;

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
