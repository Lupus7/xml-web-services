package team10.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@RestController
public class UserApplication {
	@RequestMapping("/health")
	public String health() {
		return "OK";
	}

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
