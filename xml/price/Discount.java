
package price;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Discount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Discount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="percent" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="minDays" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="prices" type="{xml-web-services-price}Price" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Discount", namespace = "xml-web-services-price", propOrder = {
    "id",
    "percent",
    "minDays",
    "prices"
})
public class Discount {

    @XmlElement(namespace = "xml-web-services-price")
    protected long id;
    @XmlElement(namespace = "xml-web-services-price")
    protected int percent;
    @XmlElement(namespace = "xml-web-services-price")
    protected int minDays;
    @XmlElement(namespace = "xml-web-services-price")
    protected List<Price> prices;

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
     * Gets the value of the percent property.
     * 
     */
    public int getPercent() {
        return percent;
    }

    /**
     * Sets the value of the percent property.
     * 
     */
    public void setPercent(int value) {
        this.percent = value;
    }

    /**
     * Gets the value of the minDays property.
     * 
     */
    public int getMinDays() {
        return minDays;
    }

    /**
     * Sets the value of the minDays property.
     * 
     */
    public void setMinDays(int value) {
        this.minDays = value;
    }

    /**
     * Gets the value of the prices property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prices property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrices().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Price }
     * 
     * 
     */
    public List<Price> getPrices() {
        if (prices == null) {
            prices = new ArrayList<Price>();
        }
        return this.prices;
    }

}
