//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 10:58:59 AM CEST 
//


package com.car_rent.agent_api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RateDetails complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RateDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="approved" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="booking" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="carId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="rater" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="recomment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateDetails", propOrder = {
    "id",
    "rate",
    "comment",
    "approved",
    "booking",
    "carId",
    "rater",
    "recomment"
})
public class RateDetails {

    protected long id;
    protected int rate;
    @XmlElement(required = true)
    protected String comment;
    protected boolean approved;
    protected long booking;
    protected long carId;
    @XmlElement(required = true)
    protected String rater;
    @XmlElement(required = true)
    protected String recomment;

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     */
    public int getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     */
    public void setRate(int value) {
        this.rate = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

    /**
     * Gets the value of the approved property.
     * 
     */
    public boolean isApproved() {
        return approved;
    }

    /**
     * Sets the value of the approved property.
     * 
     */
    public void setApproved(boolean value) {
        this.approved = value;
    }

    /**
     * Gets the value of the booking property.
     * 
     */
    public long getBooking() {
        return booking;
    }

    /**
     * Sets the value of the booking property.
     * 
     */
    public void setBooking(long value) {
        this.booking = value;
    }

    /**
     * Gets the value of the carId property.
     * 
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets the value of the carId property.
     * 
     */
    public void setCarId(long value) {
        this.carId = value;
    }

    /**
     * Gets the value of the rater property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRater() {
        return rater;
    }

    /**
     * Sets the value of the rater property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRater(String value) {
        this.rater = value;
    }

    /**
     * Gets the value of the recomment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecomment() {
        return recomment;
    }

    /**
     * Sets the value of the recomment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecomment(String value) {
        this.recomment = value;
    }

}
