package shopping.coor.jwt;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import shopping.coor.payload.request.LoginRequest;
import shopping.coor.serviceImpl.UserDetailsImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthControllerTest {

    @Autowired AuthenticationManager authenticationManager;
    @Autowired JwtUtils jwtUtils;
    // SecurityContextHolder ->" getContext() " 중간자 -> Authentication -> principal
    @Test
    @Transactional
    public void 토큰생성() throws Exception {
//        Authentication authentication = authenticationManager.authenticate();
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("user", "123123");
        System.out.println(user);
        Authentication authentication = authenticationManager.authenticate(user);
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken("user", "123123"));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        SecurityContextHolder.getContext().setAuthentication(authentication);  // 시큐리티 저장소인 SecurityContextHolder에 저장
        String jwt = jwtUtils.generateJwtToken(authentication);
        System.out.println(jwt);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        System.out.println("userDetails = " + userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        System.out.println("roles = " + roles);



    }
}