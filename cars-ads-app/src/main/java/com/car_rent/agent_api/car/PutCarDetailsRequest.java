
package com.car_rent.agent_api.car;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CarDetails" type="{http://car-rent.com/agent-api}CarDetails"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "carDetails"
})
@XmlRootElement(name = "putCarDetailsRequest", namespace = "http://car-rent.com/agent-api")
public class PutCarDetailsRequest {

    @XmlElement(name = "CarDetails", namespace = "http://car-rent.com/agent-api", required = true)
    protected CarDetails carDetails;

    /**
     * Gets the value of the carDetails property.
     * 
     * @return
     *     possible object is
     *     {@link CarDetails }
     *     
     */
    public CarDetails getCarDetails() {
        return carDetails;
    }

    /**
     * Sets the value of the carDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarDetails }
     *     
     */
    public void setCarDetails(CarDetails value) {
        this.carDetails = value;
    }

}
