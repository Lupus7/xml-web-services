
package price;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Price complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Price"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="long" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="priceKm" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="priceCDW" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ad" type="{xml-web-services-cars}Ad"/&gt;
 *         &lt;element name="discount" type="{xml-web-services-price}Discount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Price", namespace = "xml-web-services-price", propOrder = {
    "_long",
    "date",
    "price",
    "priceKm",
    "priceCDW",
    "ad",
    "discount"
})
public class Price {

    @XmlElement(name = "long", namespace = "xml-web-services-price")
    protected long _long;
    @XmlElement(namespace = "xml-web-services-price", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar date;
    @XmlElement(namespace = "xml-web-services-price")
    protected double price;
    @XmlElement(namespace = "xml-web-services-price")
    protected double priceKm;
    @XmlElement(namespace = "xml-web-services-price")
    protected double priceCDW;
    @XmlElement(namespace = "xml-web-services-price", required = true)
    protected Ad ad;
    @XmlElement(namespace = "xml-web-services-price")
    protected Discount discount;

    /**
     * Gets the value of the long property.
     * 
     */
    public long getLong() {
        return _long;
    }

    /**
     * Sets the value of the long property.
     * 
     */
    public void setLong(long value) {
        this._long = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceKm property.
     * 
     */
    public double getPriceKm() {
        return priceKm;
    }

    /**
     * Sets the value of the priceKm property.
     * 
     */
    public void setPriceKm(double value) {
        this.priceKm = value;
    }

    /**
     * Gets the value of the priceCDW property.
     * 
     */
    public double getPriceCDW() {
        return priceCDW;
    }

    /**
     * Sets the value of the priceCDW property.
     * 
     */
    public void setPriceCDW(double value) {
        this.priceCDW = value;
    }

    /**
     * Gets the value of the ad property.
     * 
     * @return
     *     possible object is
     *     {@link Ad }
     *     
     */
    public Ad getAd() {
        return ad;
    }

    /**
     * Sets the value of the ad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ad }
     *     
     */
    public void setAd(Ad value) {
        this.ad = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link Discount }
     *     
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Discount }
     *     
     */
    public void setDiscount(Discount value) {
        this.discount = value;
    }

}
