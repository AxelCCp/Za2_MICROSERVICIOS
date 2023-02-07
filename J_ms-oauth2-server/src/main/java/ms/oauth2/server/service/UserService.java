package ms.oauth2.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ms.commons.users.server.models.entity.User;
import ms.oauth2.server.feign.IUserFeignClient;

@Service
public class UserService implements UserDetailsService, IUserService{

	@Override
	public User findByUsername(String username) {
		return userfeign.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userfeign.findByUsername(username);
		
		if(user == null) {
			log.error("Login failed. The user '" + username + "' does not exist in the system!");
			throw new UsernameNotFoundException("Login failed. The user '" + username + "' does not exist in the system!");
		}
		
		List <GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority->log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		log.info("Authenticated user: " + username);
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(),true, true, true, authorities);
	}

	@Override
	public User update(User user, Long id) {
		return userfeign.update(user, id);
	}
	
	@Autowired
	private IUserFeignClient userfeign;
	
	private Logger log = LoggerFactory.getLogger(UserService.class);

	
}
