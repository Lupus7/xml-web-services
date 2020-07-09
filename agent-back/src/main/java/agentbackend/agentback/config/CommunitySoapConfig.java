package agentbackend.agentback.config;

import agentbackend.agentback.soapClient.CommunitySoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CommunitySoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerComm() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.car_rent.agent_api.wsdl");
        return marshaller;
    }

    @Bean
    public CommunitySoapClient communityClient(Jaxb2Marshaller marshallerComm) {
        CommunitySoapClient client = new CommunitySoapClient();
        client.setDefaultUri("http://localhost:8080/community/ws");
        client.setMarshaller(marshallerComm);
        client.setUnmarshaller(marshallerComm);
        return client;
    }
}
