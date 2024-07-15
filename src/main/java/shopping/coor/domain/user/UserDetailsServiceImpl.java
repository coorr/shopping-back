package shopping.coor.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
		User user = userRepository.findByUsernameJoinRoles(username);
//				.orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

		return UserDetailsImpl.build(user);
	}
}
