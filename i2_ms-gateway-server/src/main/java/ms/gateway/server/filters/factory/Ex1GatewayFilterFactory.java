package ms.gateway.server.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class Ex1GatewayFilterFactory extends AbstractGatewayFilterFactory<Ex1GatewayFilterFactory.Configuration>{

	public static class Configuration {
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCookieValue() {
			return cookieValue;
		}
		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}
		public String getCookieName() {
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
		private String message;
		private String cookieValue;
		private String cookieName;
	}
	
	public Ex1GatewayFilterFactory() {
		super(Configuration.class);
	}

	
	@Override
	public GatewayFilter apply(Configuration config) {
		return (exchange, chain) ->{
			logger.info("Running the PRE Gateway filter factory: " + config.message);
			return  chain.filter(exchange).then(Mono.fromRunnable(()-> {
				Optional.ofNullable(config.cookieValue).ifPresent(cookie->{
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, cookie).build());
				});
				
				logger.info("Running the POST Gateway filter factory: " + config.message);
			}));
		};
	}
	private final Logger logger = LoggerFactory.getLogger(Ex1GatewayFilterFactory.class);
	
}
