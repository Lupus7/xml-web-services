package carRent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@RestController
public class CarRentApplication {

	@RequestMapping("/health")
	public String health() {
		return "OK";
	}

	public static void main(String[] args) {
		SpringApplication.run(CarRentApplication.class, args);
	}

}
