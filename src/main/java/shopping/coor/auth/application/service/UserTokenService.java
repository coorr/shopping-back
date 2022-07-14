package shopping.coor.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.auth.application.exception.UserNotFoundException;
import shopping.coor.auth.application.service.body.CreateTokenBody;
import shopping.coor.auth.application.jwt.JwtUtils;
import shopping.coor.auth.application.service.payload.CreateTokenPayload;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserTokenService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public CreateTokenBody createToken(CreateTokenPayload payload) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
        } catch (Exception e) {
            throw new UserNotFoundException("아이디 또는 비밀번호를 찾을 수 없습니다.");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return CreateTokenBody.builder()
                .token(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();

    }
}
