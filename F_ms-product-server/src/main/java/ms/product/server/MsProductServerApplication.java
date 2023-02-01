package ms.product.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsProductServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsProductServerApplication.class, args);
	}

}
