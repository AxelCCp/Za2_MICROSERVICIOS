package ms.item.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RibbonClient(name="ms-products-server")
@EnableFeignClients 
@SpringBootApplication
public class MsItemServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsItemServerApplication.class, args);
	}

}
