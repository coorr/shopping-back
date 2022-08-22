package shopping.coor.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import shopping.coor.auth.application.service.UserDetailsImpl;
import shopping.coor.auth.domain.Role.ERole;
import shopping.coor.auth.domain.Role.Role;
import shopping.coor.auth.domain.User.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        Role role = new Role(1L, ERole.ROLE_USER);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = User.builder().email("test@naver.com").username("test").roles(roleSet).build();

        UserDetails userDetails = UserDetailsImpl.build(user);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, "password", List.of(new SimpleGrantedAuthority(role.toString())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
