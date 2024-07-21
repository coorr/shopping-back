package shopping.coor.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shopping.coor.filter.JwtExceptionFilter;
import shopping.coor.common.exception.CustomForbiddenEntryPoint;
import shopping.coor.filter.AuthTokenFilter;
import shopping.coor.domain.user.signin.JwtService;
import shopping.coor.domain.user.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;
	private final CustomForbiddenEntryPoint unauthorizedHandler;
	private final JwtService jwtService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(){
		return web -> {
			web.ignoring()
					.antMatchers(
							"/static/**",
							"/test/**",
							"/test",
							"/api/user/**",
							"/api/user/signin"
							);
		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/items/**").permitAll()
				.antMatchers("/api/basket/**").permitAll()
				.antMatchers("/api/order/**").permitAll()
				.antMatchers("/api/user/**").permitAll()
				.anyRequest().authenticated();


		http.addFilterBefore(new AuthTokenFilter(jwtService, userDetailsService), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(new JwtExceptionFilter(), AuthTokenFilter.class);
	}


}














