
package com.car_rent.agent_api.car;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.car_rent.agent_api.car package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.car_rent.agent_api.car
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PutCarDetailsRequest }
     * 
     */
    public PutCarDetailsRequest createPutCarDetailsRequest() {
        return new PutCarDetailsRequest();
    }

    /**
     * Create an instance of {@link CarDetails }
     * 
     */
    public CarDetails createCarDetails() {
        return new CarDetails();
    }

    /**
     * Create an instance of {@link PutCarDetailsResponse }
     * 
     */
    public PutCarDetailsResponse createPutCarDetailsResponse() {
        return new PutCarDetailsResponse();
    }

}
