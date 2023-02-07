package ms.oauth2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class MsOauth2ServerApplication implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(MsOauth2ServerApplication.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		String password = "1234567";
		for(int i=0; i<4; i++) {
			String passwordBcrypt = passwordEncode.encode(password);
			System.out.println(passwordBcrypt);
		}
	}

	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
}
