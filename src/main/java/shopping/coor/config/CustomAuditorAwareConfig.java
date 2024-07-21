package shopping.coor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class CustomAuditorAwareConfig implements AuditorAware<Long> {

    private final HttpServletRequest request;

    @Override
    public Optional<Long> getCurrentAuditor() {
//        UserInfo userInfo = (UserInfo) request.getAttribute(LoginCheckInterceptor.LOGIN_ATTRIBUTE_NAME);
//        if (userInfo == null) {
//            return Optional.ofNullable(1001L);
//        }
//        return Optional.ofNullable(userInfo.getMemberId());
        return Optional.ofNullable(1001L);
    }
}
