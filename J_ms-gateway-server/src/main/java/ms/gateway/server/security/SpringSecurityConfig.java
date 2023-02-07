package ms.gateway.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SpringSecurityConfig {
	
	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {
		
		return http.authorizeExchange().pathMatchers("/g/security/oauth/**").permitAll() 
				
				.pathMatchers(HttpMethod.GET, "/g/prod", "/g/item", "/g/users/userdao").permitAll()
				.pathMatchers(HttpMethod.GET, "/g/prod/{id}", "/g/item/id/{id}/q/{q}", "/g/users/userdao/{id}").hasAnyRole("ADMIN","USER")
				.pathMatchers(HttpMethod.POST, "/g/prod/create", "/g/item/create", "/g/users/userdao").hasRole("ADMIN")
				.pathMatchers(HttpMethod.PUT, "/g/prod/edit/{id}", "/g/item/editar/{id}", "/g/user/userdao/{id}").hasRole("ADMIN")
				.pathMatchers(HttpMethod.DELETE, "/g/prod/delete/{id}", "/g/item/delete/{id}", "/g/users/userdao/{id}").hasRole("ADMIN")
				
				.anyExchange().authenticated()
				.and()
				.addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.csrf().disable()
				.build();
	}
	
	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
}
