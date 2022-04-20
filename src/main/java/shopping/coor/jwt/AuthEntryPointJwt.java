package shopping.coor.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component  // 인증,인가 예외발생시 처리
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		log.error("Unauthorized error: {}", authException.getMessage());

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().print("invalid authority");
	}
}
