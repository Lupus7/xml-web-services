//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 05:22:49 AM CEST 
//


package com.car_rent.agent_api;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.car_rent.agent_api package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.car_rent.agent_api
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllSpecsRequest }
     * 
     */
    public GetAllSpecsRequest createGetAllSpecsRequest() {
        return new GetAllSpecsRequest();
    }

    /**
     * Create an instance of {@link GetAllSpecsResponse }
     * 
     */
    public GetAllSpecsResponse createGetAllSpecsResponse() {
        return new GetAllSpecsResponse();
    }

    /**
     * Create an instance of {@link BrandDetails }
     * 
     */
    public BrandDetails createBrandDetails() {
        return new BrandDetails();
    }

}
