package shopping.coor.controller;

import java.net.UnknownHostException;
import java.util.List;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import shopping.coor.model.User;
import shopping.coor.repository.user.UserRepository;
import shopping.coor.repository.user.dto.LoginRequest;
import shopping.coor.repository.user.dto.SignupRequest;
import shopping.coor.repository.user.dto.MessageResponse;
import shopping.coor.service.UserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final UserRepository userRepository;


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






















