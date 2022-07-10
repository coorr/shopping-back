package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.auth.domain.User.User;
import shopping.coor.auth.presentation.http.request.MessageResponse;
import shopping.coor.auth.presentation.http.request.SignupRequest;

import java.util.List;


public interface UserService {
    ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest);
}
