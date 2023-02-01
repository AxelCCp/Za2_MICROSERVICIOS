package ms.gateway.server.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.stereotype.Component;

@Component
public class GatewayGlobalFilter1 implements GlobalFilter, Ordered{

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		logger.info("Running the pre filter.");
		exchange.getRequest().mutate().headers(h->h.add("token", "d5g7h8j9s4a3k0n9b8v7f6t6r5e4o0u8e3njrf7ycdc39nfruhswcd8ur478cdfrkjasjhr44456jig5y6u8w2"));
		return chain.filter(exchange).then(Mono.fromRunnable(()->{
			logger.info("Running the post filter.");
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(valor -> {
				exchange.getResponse().getHeaders().add("token", valor);
			});
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
		}));
	}
	
	private final static Logger  logger = LoggerFactory.getLogger(GatewayGlobalFilter1.class);
}
