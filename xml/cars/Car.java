
package cars;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Car complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Car"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="totalMileage" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="allowedMileage" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="childrenSeats" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="colDamProtection" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="owned" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="brand" type="{xml-web-services}Brand"/&gt;
 *         &lt;element name="model" type="{xml-web-services}Model"/&gt;
 *         &lt;element name="carClass" type="{xml-web-services}Class"/&gt;
 *         &lt;element name="fuel" type="{xml-web-services}Fuel"/&gt;
 *         &lt;element name="transmission" type="{xml-web-services}Transmission"/&gt;
 *         &lt;element name="bookings" type="{xml-web-services}Booking" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="images" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="ads" type="{xml-web-services}Ad" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Car", namespace = "xml-web-services", propOrder = {
    "id",
    "totalMileage",
    "allowedMileage",
    "childrenSeats",
    "description",
    "colDamProtection",
    "owned",
    "brand",
    "model",
    "carClass",
    "fuel",
    "transmission",
    "bookings",
    "images",
    "ads"
})
public class Car {

    @XmlElement(namespace = "xml-web-services")
    protected long id;
    @XmlElement(namespace = "xml-web-services")
    protected double totalMileage;
    @XmlElement(namespace = "xml-web-services")
    protected double allowedMileage;
    @XmlElement(namespace = "xml-web-services")
    protected int childrenSeats;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected String description;
    @XmlElement(namespace = "xml-web-services")
    protected boolean colDamProtection;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected String owned;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected Brand brand;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected Model model;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected Class carClass;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected Fuel fuel;
    @XmlElement(namespace = "xml-web-services", required = true)
    protected Transmission transmission;
    @XmlElement(namespace = "xml-web-services")
    protected List<Booking> bookings;
    @XmlElement(namespace = "xml-web-services")
    protected List<String> images;
    @XmlElement(namespace = "xml-web-services")
    protected List<Ad> ads;

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
     * Gets the value of the totalMileage property.
     * 
     */
    public double getTotalMileage() {
        return totalMileage;
    }

    /**
     * Sets the value of the totalMileage property.
     * 
     */
    public void setTotalMileage(double value) {
        this.totalMileage = value;
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
     * Gets the value of the childrenSeats property.
     * 
     */
    public int getChildrenSeats() {
        return childrenSeats;
    }

    /**
     * Sets the value of the childrenSeats property.
     * 
     */
    public void setChildrenSeats(int value) {
        this.childrenSeats = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the colDamProtection property.
     * 
     */
    public boolean isColDamProtection() {
        return colDamProtection;
    }

    /**
     * Sets the value of the colDamProtection property.
     * 
     */
    public void setColDamProtection(boolean value) {
        this.colDamProtection = value;
    }

    /**
     * Gets the value of the owned property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOwned() {
        return owned;
    }

    /**
     * Sets the value of the owned property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOwned(String value) {
        this.owned = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link Brand }
     *     
     */
    public Brand getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link Brand }
     *     
     */
    public void setBrand(Brand value) {
        this.brand = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link Model }
     *     
     */
    public Model getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link Model }
     *     
     */
    public void setModel(Model value) {
        this.model = value;
    }

    /**
     * Gets the value of the carClass property.
     * 
     * @return
     *     possible object is
     *     {@link Class }
     *     
     */
    public Class getCarClass() {
        return carClass;
    }

    /**
     * Sets the value of the carClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link Class }
     *     
     */
    public void setCarClass(Class value) {
        this.carClass = value;
    }

    /**
     * Gets the value of the fuel property.
     * 
     * @return
     *     possible object is
     *     {@link Fuel }
     *     
     */
    public Fuel getFuel() {
        return fuel;
    }

    /**
     * Sets the value of the fuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fuel }
     *     
     */
    public void setFuel(Fuel value) {
        this.fuel = value;
    }

    /**
     * Gets the value of the transmission property.
     * 
     * @return
     *     possible object is
     *     {@link Transmission }
     *     
     */
    public Transmission getTransmission() {
        return transmission;
    }

    /**
     * Sets the value of the transmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link Transmission }
     *     
     */
    public void setTransmission(Transmission value) {
        this.transmission = value;
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
     * {@link Booking }
     * 
     * 
     */
    public List<Booking> getBookings() {
        if (bookings == null) {
            bookings = new ArrayList<Booking>();
        }
        return this.bookings;
    }

    /**
     * Gets the value of the images property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the images property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getImages() {
        if (images == null) {
            images = new ArrayList<String>();
        }
        return this.images;
    }

    /**
     * Gets the value of the ads property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ads property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ad }
     * 
     * 
     */
    public List<Ad> getAds() {
        if (ads == null) {
            ads = new ArrayList<Ad>();
        }
        return this.ads;
    }

}
