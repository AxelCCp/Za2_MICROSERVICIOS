package ms.item.server;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Bean("RestTemplateClient")
	@LoadBalanced   										//FOR THE load balancing WITH REST TEMPLATE.
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
	
}
