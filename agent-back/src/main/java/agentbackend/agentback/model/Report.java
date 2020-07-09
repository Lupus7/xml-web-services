
package agentbackend.agentback.model;

import javax.persistence.*;
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
 *         &lt;element name="booking" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
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
@Entity
@Table(name="report")
public class Report {

    @XmlElement(namespace = "xml-web-services-community")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_seq_gen")
    @SequenceGenerator(name="report_id_seq_gen", sequenceName = "report_id_seq", allocationSize = 1)
    protected long id;
    @XmlElement(namespace = "xml-web-services-community", required = true)
    protected String extraInfo;
    @XmlElement(namespace = "xml-web-services-community")
    protected double allowedMileage;
    @XmlElement(namespace = "xml-web-services-community")
    protected long booking;

    @Column(unique = true, nullable = true)
    protected Long serviceId;

    public Report() {
    }

    public Report(long id, String extraInfo, double allowedMileage, long booking) {
        this.id = id;
        this.extraInfo = extraInfo;
        this.allowedMileage = allowedMileage;
        this.booking = booking;
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


    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
