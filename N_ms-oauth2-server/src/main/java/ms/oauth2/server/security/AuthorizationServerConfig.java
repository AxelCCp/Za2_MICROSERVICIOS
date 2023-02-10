package ms.oauth2.server.security;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//1 - usa esta con dato en duro, si no se levanta spring cloud config.
//2 - levantando spring cloud config, lee la propiedad desde el application properties q está en github.

@RefreshScope     //ACTUATOR : IF THE SPRING CLOUD CONFIG CONFIGURATION FILES ARE UPDATED, IT WILL TAKE THE DATA WITHOUT THE NEED TO RESTART THE MICRO-SERVICE.
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		//clients.inMemory().withClient("angularfrontwebapp")									//1		
		clients.inMemory().withClient(clientId)		//2		
		//.secret(passwordEncoder.encode("1234567"))											//1
		.secret(passwordEncoder.encode(clientSecret))	//2
		.scopes("read","write")
		.authorizedGrantTypes("password", "refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); 
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenAditionalInformation,accessTokenConverter()));  //I set additional token information.
		endpoints.authenticationManager(authenticationManager)  												//registry of authenticationManager in AuthorizationServer.	
		.tokenStore(tokenStore())																				//token store creation. here i save de Token with the token converter information.
		.accessTokenConverter(accessTokenConverter())															//token converter configuration.
		.tokenEnhancer(tokenEnhancerChain);
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter	= new JwtAccessTokenConverter();
		//tokenConverter.setSigningKey("secret_code_1234567");																				//1	//key to sign the token.		
		tokenConverter.setSigningKey(Base64.getEncoder().encodeToString(jwtkey.getBytes()));		//2
		return tokenConverter;
	}
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private TokenAditionalInformation tokenAditionalInformation;
	
	//@Autowired
	//private Environment env; 
	
	@Value("${springcloudconfig.jwtkey}")
	private String jwtkey;
	@Value("${config.security.oauth.client.id}")
	private String clientId;
	@Value("${config.security.oauth.client.secret}")
	private String clientSecret;
	
	
	
}
