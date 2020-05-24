package team10.codebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
@RestController
public class CodebookApplication {
    @RequestMapping("/health")
    public String health() {
        return "OK";
    }

    public static void main(String[] args) {
        SpringApplication.run(CodebookApplication.class, args);
    }

}
