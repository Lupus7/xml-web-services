package agentbackend.agentback.config;

import agentbackend.agentback.soapClient.AdSoapClient;
import agentbackend.agentback.soapClient.UserSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class UserSoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerUser() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.car_rent.agent_api.wsdl");
        return marshaller;
    }

    @Bean
    public UserSoapClient userClient(Jaxb2Marshaller marshallerUser) {
        UserSoapClient client = new UserSoapClient();
        client.setDefaultUri("http://localhost:8080/user/ws");
        client.setMarshaller(marshallerUser);
        client.setUnmarshaller(marshallerUser);
        return client;
    }
}
