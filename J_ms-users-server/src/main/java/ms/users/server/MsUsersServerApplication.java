package ms.users.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("ms.commons.users.server.models.entity")
@SpringBootApplication
public class MsUsersServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsUsersServerApplication.class, args);
	}

}
