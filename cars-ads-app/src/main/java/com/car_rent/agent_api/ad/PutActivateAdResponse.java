
package com.car_rent.agent_api.ad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for putActivateAdResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="putActivateAdResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isOk" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "putActivateAdResponse", namespace = "http://car-rent.com/agent-api", propOrder = {
    "isOk"
})
public class PutActivateAdResponse {

    @XmlElement(namespace = "http://car-rent.com/agent-api")
    protected boolean isOk;

    /**
     * Gets the value of the isOk property.
     * 
     */
    public boolean isIsOk() {
        return isOk;
    }

    /**
     * Sets the value of the isOk property.
     * 
     */
    public void setIsOk(boolean value) {
        this.isOk = value;
    }

}
