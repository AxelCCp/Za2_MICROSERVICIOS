package ms.oauth2.server.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import feign.FeignException;
import ms.commons.users.server.models.entity.User;
import ms.oauth2.server.service.IUserService;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		//ON THE NEW VERSIONS, SPRING DOES A DOBLE VALIDATION, ONE BY THE APPLICATION NAME AND OTHER BY THE USER, THEN WITH THE IF() i LEAVE ONLY THE VALIDATION BY USER.
		if(authentication.getName().equalsIgnoreCase("angularfrontwebapp")) {
			return;
		}
		UserDetails userdetail = (UserDetails) authentication.getPrincipal();
		System.out.println("Success login: " + userdetail.getUsername());
		log.info("Success login: " + userdetail.getUsername());
		
		User user = userService.findByUsername(authentication.getName());
		if(user.getIntentos() != null && user.getIntentos() > 0) {
			user.setIntentos(0);
			userService.update(user, user.getId());
		}
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		log.error("Login error: " + exception.getMessage());
		try {
			User user = userService.findByUsername(authentication.getName());
			if(user.getIntentos()==null) {
				user.setIntentos(0);
			}
			user.setIntentos(user.getIntentos()+1);
			log.info("Number of attemps: " + user.getIntentos());
			if(user.getIntentos()>=3) {
				log.error(String.format("User %s disabled for exceeding maximum number of attempts.", user.getUsername()));
				user.setEnabled(false);
			}
			userService.update(user, user.getId());
		}catch(FeignException e) {
			log.error(String.format("The user %s does not exist in the system", authentication.getName()));
		}
	}

	@Autowired
	private IUserService userService;
	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
}
