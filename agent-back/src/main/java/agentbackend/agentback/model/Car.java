
package agentbackend.agentback.model;

import javax.persistence.*;
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
 *         &lt;element name="owner" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="brand" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="carClass" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fuel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="transmission" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="bookings" type="{http://www.w3.org/2001/XMLSchema}long" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Car", namespace = "xml-web-services-cars", propOrder = {
    "id",
    "totalMileage",
    "allowedMileage",
    "childrenSeats",
    "description",
    "colDamProtection",
    "owner",
    "brand",
    "model",
    "carClass",
    "fuel",
    "transmission",
})
@Entity
@Table(name="car")
public class Car {

    @XmlElement(namespace = "xml-web-services-cars")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id_seq_gen")
    @SequenceGenerator(name="car_id_seq_gen", sequenceName = "car_id_seq", allocationSize = 1)
    protected long id;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars")
    protected double totalMileage;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars")
    protected double allowedMileage;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars")
    protected int childrenSeats;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String description;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars")
    protected boolean colDamProtection;
    @Column(name = "owner", nullable = false)
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String owner;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String brand;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String model;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String carClass;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String fuel;
    @Column()
    @XmlElement(namespace = "xml-web-services-cars", required = true)
    protected String transmission;
    @Column()
    private Long serviceId;

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
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the carClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarClass() {
        return carClass;
    }

    /**
     * Sets the value of the carClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarClass(String value) {
        this.carClass = value;
    }

    /**
     * Gets the value of the fuel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuel() {
        return fuel;
    }

    /**
     * Sets the value of the fuel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuel(String value) {
        this.fuel = value;
    }

    /**
     * Gets the value of the transmission property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransmission() {
        return transmission;
    }

    /**
     * Sets the value of the transmission property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransmission(String value) {
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
     * {@link Long }
     * 
     * 
     */

    public Car() {
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
