package shopping.coor.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import shopping.coor.domain.user.UserDetailsImpl;
import shopping.coor.domain.user.role.ERole;
import shopping.coor.domain.user.role.Role;
import shopping.coor.domain.user.User;

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
