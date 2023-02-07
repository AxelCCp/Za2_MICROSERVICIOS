package ms.oauth2.server.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import ms.commons.users.server.models.entity.User;
import ms.oauth2.server.service.IUserService;

@Component
public class TokenAditionalInformation implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Map<String,Object>userInformation = new HashMap<String,Object>();
		User user = userService.findByUsername(authentication.getName());
		userInformation.put("name", user.getName());
		userInformation.put("lastname", user.getLastname());
		userInformation.put("email", user.getEmail());
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(userInformation);     					 //OAuth2AccessToken accessToken very generic, then i do a cast to to DefaultOAuth2AccessToken. It is ok for me. 
		return accessToken;
	}

	@Autowired
	private IUserService userService;
}
