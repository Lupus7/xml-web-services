package CarsAdsApp.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "cars")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCar(XsdSchema carsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CarsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(SoapProperties.NAMESPACE_URI);
        wsdl11Definition.setSchema(carsSchema);
        return wsdl11Definition;
    }

    @Bean(name = "ads")
    public DefaultWsdl11Definition defaultWsdl11DefinitionAd(XsdSchema adsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("AdsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(SoapProperties.NAMESPACE_URI);
        wsdl11Definition.setSchema(adsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema carsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("car-details.xsd"));
    }

    @Bean
    public XsdSchema adsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ad-details.xsd"));
    }
}
