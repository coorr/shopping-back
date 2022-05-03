package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import shopping.coor.model.User;
import shopping.coor.repository.user.dto.LoginRequest;
import shopping.coor.repository.user.dto.SignupRequest;
import shopping.coor.repository.user.dto.MessageResponse;

import java.util.List;


public interface UserService {
    List<User> selectAll();
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest);
}
