
package community;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Report complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Report"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="extraInfo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="allowedMileage" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="booking" type="{xml-web-services-cars}Booking"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Report", namespace = "xml-web-services-community", propOrder = {
    "id",
    "extraInfo",
    "allowedMileage",
    "booking"
})
public class Report {

    @XmlElement(namespace = "xml-web-services-community")
    protected long id;
    @XmlElement(namespace = "xml-web-services-community", required = true)
    protected String extraInfo;
    @XmlElement(namespace = "xml-web-services-community")
    protected double allowedMileage;
    @XmlElement(namespace = "xml-web-services-community", required = true)
    protected Booking booking;

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
     * Gets the value of the extraInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    /**
     * Sets the value of the extraInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtraInfo(String value) {
        this.extraInfo = value;
    }

    /**
     * Gets the value of the allowedMileage property.
     * 
     */
    public double getAllowedMileage() {
        return allowedMileage;
    }

    /**
     * Sets the value of the allowedMileage property.
     * 
     */
    public void setAllowedMileage(double value) {
        this.allowedMileage = value;
    }

    /**
     * Gets the value of the booking property.
     * 
     * @return
     *     possible object is
     *     {@link Booking }
     *     
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Sets the value of the booking property.
     * 
     * @param value
     *     allowed object is
     *     {@link Booking }
     *     
     */
    public void setBooking(Booking value) {
        this.booking = value;
    }

}
