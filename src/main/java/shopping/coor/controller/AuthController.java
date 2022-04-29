package shopping.coor.controller;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.coor.model.User;
import shopping.coor.payload.request.LoginRequest;
import shopping.coor.payload.request.SignupRequest;
import shopping.coor.payload.response.MessageResponse;
import shopping.coor.service.UserService;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;


	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws UnknownHostException {
	    return userService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.registerUser(signUpRequest);
	}

	@GetMapping("/selectAll")
	public ResponseEntity<List<User>> selectAll() {
		return ResponseEntity.ok(userService.selectAll());
	}
}






















