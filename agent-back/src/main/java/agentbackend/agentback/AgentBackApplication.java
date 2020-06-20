package agentbackend.agentback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgentBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgentBackApplication.class, args);
	}

}
