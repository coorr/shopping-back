package shopping.coor.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import shopping.coor.common.container.ErrorsResponse;
import shopping.coor.domain.user.signin.exception.InvalidTokenException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component  // 인증 처리
@Slf4j
public class CustomForbiddenEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {

		log.error("UNAUTHORIZED: {}", authException.getMessage());

		Throwable cause = authException.getCause();
		Throwable exception = (Throwable) request.getAttribute("exception");
		String errorMessage;
		if (exception instanceof InvalidTokenException) {
			errorMessage = "유효하지 않은 토큰입니다.";
		} else {
			errorMessage = "인증이 필요합니다.";
		}

		ErrorsResponse errorResponse = ErrorsResponse.create(errorMessage, null);
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
	}
}
