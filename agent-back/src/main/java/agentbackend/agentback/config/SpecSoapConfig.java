package agentbackend.agentback.config;

import agentbackend.agentback.soapClient.CarSoapClient;
import agentbackend.agentback.soapClient.SpecSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class SpecSoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerSpec() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.car_rent.agent_api.wsdl");
        return marshaller;
    }

    @Bean
    public SpecSoapClient specClient(Jaxb2Marshaller marshallerSpec) {
        SpecSoapClient client = new SpecSoapClient();
        client.setDefaultUri("http://localhost:8080/codebook/ws");
        client.setMarshaller(marshallerSpec);
        client.setUnmarshaller(marshallerSpec);
        return client;
    }

}
