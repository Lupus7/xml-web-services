//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.06.28 at 09:56:53 PM CEST 
//


package com.car_rent.agent_api;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="bundle_id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="bookings" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded"/>
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
    "bundleId",
    "bookings"
})
@XmlRootElement(name = "reserveBookingResponse")
public class ReserveBookingResponse {

    @XmlElement(name = "bundle_id")
    protected long bundleId;
    @XmlElement(type = Long.class)
    protected List<Long> bookings;

    /**
     * Gets the value of the bundleId property.
     * 
     */
    public long getBundleId() {
        return bundleId;
    }

    /**
     * Sets the value of the bundleId property.
     * 
     */
    public void setBundleId(long value) {
        this.bundleId = value;
    }

    /**
     * Gets the value of the bookings property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bookings property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBookings().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Long }
     * 
     * 
     */
    public List<Long> getBookings() {
        if (bookings == null) {
            bookings = new ArrayList<Long>();
        }
        return this.bookings;
    }

}
