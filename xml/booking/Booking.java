
package booking;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Booking complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Booking"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="state" type="{xml-web-service-booking}RequestState"/&gt;
 *         &lt;element name="place" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="created" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="loaner" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ad" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="bundle" type="{xml-web-service-booking}Bundle" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Booking", namespace = "xml-web-service-booking", propOrder = {
    "id",
    "startDate",
    "endDate",
    "state",
    "place",
    "created",
    "loaner",
    "ad",
    "bundle"
})
public class Booking {

    @XmlElement(namespace = "xml-web-service-booking")
    protected long id;
    @XmlElement(namespace = "xml-web-service-booking", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(namespace = "xml-web-service-booking", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(namespace = "xml-web-service-booking", required = true)
    @XmlSchemaType(name = "string")
    protected RequestState state;
    @XmlElement(namespace = "xml-web-service-booking", required = true)
    protected String place;
    @XmlElement(namespace = "xml-web-service-booking", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar created;
    @XmlElement(namespace = "xml-web-service-booking", required = true)
    protected String loaner;
    @XmlElement(namespace = "xml-web-service-booking")
    protected long ad;
    @XmlElement(namespace = "xml-web-service-booking")
    protected Bundle bundle;

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
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link RequestState }
     *     
     */
    public RequestState getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestState }
     *     
     */
    public void setState(RequestState value) {
        this.state = value;
    }

    /**
     * Gets the value of the place property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlace() {
        return place;
    }

    /**
     * Sets the value of the place property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlace(String value) {
        this.place = value;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreated(XMLGregorianCalendar value) {
        this.created = value;
    }

    /**
     * Gets the value of the loaner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLoaner() {
        return loaner;
    }

    /**
     * Sets the value of the loaner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLoaner(String value) {
        this.loaner = value;
    }

    /**
     * Gets the value of the ad property.
     * 
     */
    public long getAd() {
        return ad;
    }

    /**
     * Sets the value of the ad property.
     * 
     */
    public void setAd(long value) {
        this.ad = value;
    }

    /**
     * Gets the value of the bundle property.
     * 
     * @return
     *     possible object is
     *     {@link Bundle }
     *     
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * Sets the value of the bundle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Bundle }
     *     
     */
    public void setBundle(Bundle value) {
        this.bundle = value;
    }

}
