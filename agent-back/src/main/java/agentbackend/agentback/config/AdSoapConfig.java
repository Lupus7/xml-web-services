package agentbackend.agentback.config;

import agentbackend.agentback.soapClient.AdSoapClient;
import agentbackend.agentback.soapClient.CarSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class AdSoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerAd() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.car_rent.agent_api.wsdl");
        return marshaller;
    }

    @Bean
    public AdSoapClient adClient(Jaxb2Marshaller marshallerAd) {
        AdSoapClient client = new AdSoapClient();
        client.setDefaultUri("http://localhost:8080/cars-ads/ws");
        client.setMarshaller(marshallerAd);
        client.setUnmarshaller(marshallerAd);
        return client;
    }
}
