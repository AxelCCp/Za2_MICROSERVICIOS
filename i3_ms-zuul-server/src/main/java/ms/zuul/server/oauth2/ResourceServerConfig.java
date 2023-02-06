package ms.zuul.server.oauth2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@RefreshScope			//ACTUATOR : IF THE SPRING CLOUD CONFIG CONFIGURATION FILES ARE UPDATED, IT WILL TAKE THE DATA WITHOUT THE NEED TO RESTART THE MICRO-SERVICE.
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/z/security/oauth/**").permitAll()
		.antMatchers(HttpMethod.GET, "/z/prod", "/z/item", "/z/users/userdao").permitAll()
		.antMatchers(HttpMethod.GET, "/z/prod/{id}","/z/item/id/{id}/q/{q}", "/z/users/userdao/{id}").hasAnyRole("ADMIN","USER")
		.antMatchers(HttpMethod.POST, "/z/prod/create", "/z/item/create", "/z/users/userdao").hasRole("ADMIN")
		.antMatchers(HttpMethod.PUT, "/z/prod/edit/{id}", "/z/item/editar/{id}", "/z/user/userdao/{id}").hasRole("ADMIN")
		.antMatchers(HttpMethod.DELETE, "/z/prod/delete/{id}", "/z/item/delete/{id}", "/z/users/userdao/{id}").hasRole("ADMIN")
		.and().cors().configurationSource(corsConfigurationSource());
	}
	
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter	= new JwtAccessTokenConverter();
		//tokenConverter.setSigningKey("secret_code_1234567");											//1 - usa esta con dato en duro, si no se levanta spring cloud config.
		tokenConverter.setSigningKey(env.getProperty("config.security.oauth.jwt.key"));					//2 - levantando spring cloud config, lee la propiedad desde el application properties q está en github.
		return tokenConverter;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization","Content-Type"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}
	
	@Bean																								//el bean es para q quede configurado en todo spring y no solo en spring security.
	public FilterRegistrationBean<CorsFilter>corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); //4.4
		return bean;
	}
	
	@Autowired
	private Environment env; 
}
