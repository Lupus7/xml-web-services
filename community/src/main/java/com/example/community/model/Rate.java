
package com.example.community.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rate"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="rate" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="comment" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="approved" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="booking" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="carId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="rater" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rate", namespace = "xml-web-services-community", propOrder = {
    "id",
    "rate",
    "comment",
    "approved",
    "booking",
    "carId",
    "rater"

})
@Entity
@Table(name = "rate")
public class Rate {

    @XmlElement(namespace = "xml-web-services-community")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rate_id_seq_gen")
    @SequenceGenerator(name = "rate_id_seq_gen", sequenceName = "rate_id_seq", allocationSize = 1)
    protected long id;
    @XmlElement(namespace = "xml-web-services-community")
    @Column(nullable = false)
    protected int rate;
    @XmlElement(namespace = "xml-web-services-community", required = true)
    @Column()
    protected String comment;
    @XmlElement(namespace = "xml-web-services-community")
    @Column(nullable = false)
    protected boolean approved;
    @XmlElement(namespace = "xml-web-services-community")
    @Column(nullable = false)
    protected long booking;
    @XmlElement(namespace = "xml-web-services-community")
    @Column(nullable = false)
    protected long carId;
    @XmlElement(namespace = "xml-web-services-community")
    @Column(nullable = false)
    protected String rater;
    @XmlElement(namespace = "xml-web-services-community")
    protected String recomment;

    public String getRecomment() {
        return recomment;
    }

    public void setRecomment(String recomment) {
        this.recomment = recomment;
    }

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
    /**
     * Gets the value of the comment property.
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
     * Sets the value of the comment property.
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

    public Rate() {
    }
}
