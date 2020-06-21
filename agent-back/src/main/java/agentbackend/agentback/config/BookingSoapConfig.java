package agentbackend.agentback.config;

import agentbackend.agentback.soapClient.BookingSoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class BookingSoapConfig {

    @Bean
    public Jaxb2Marshaller marshallerBooking() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.car_rent.agent_api.wsdl");
        return marshaller;
    }


    @Bean
    public BookingSoapClient bookingClient(Jaxb2Marshaller marshallerBooking) {
        BookingSoapClient client = new BookingSoapClient();
        client.setDefaultUri("http://localhost:8080/rent/ws");
        client.setMarshaller(marshallerBooking);
        client.setUnmarshaller(marshallerBooking);
        return client;
    }
}
