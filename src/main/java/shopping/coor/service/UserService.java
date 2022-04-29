package shopping.coor.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shopping.coor.model.User;
import shopping.coor.payload.request.LoginRequest;
import shopping.coor.payload.request.SignupRequest;
import shopping.coor.payload.response.MessageResponse;

import java.util.List;


public interface UserService {
    List<User> selectAll();
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest);
}
