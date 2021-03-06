package agentbackend.agentback.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * <p>Java class for Message complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Message"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="body" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="booking" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Message", namespace = "xml-web-services-community", propOrder = {
        "id",
        "body",
        "date",
        "booking"
})
@Entity
@Table(name = "message")
public class Message {

    @XmlElement(namespace = "xml-web-services-community")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_seq_gen")
    @SequenceGenerator(name = "message_id_seq_gen", sequenceName = "message_id_seq", allocationSize = 1)
    protected long id;
    @XmlElement(namespace = "xml-web-services-community", required = true)
    @Column(unique = false, nullable = false, columnDefinition = "text")
    protected String body;
    @XmlElement(namespace = "xml-web-services-community", required = true)
    @XmlSchemaType(name = "date")
    @Column(unique = false, nullable = false)
    protected LocalDateTime date;

    @Column(unique = false)
    @XmlElement(namespace = "xml-web-services-community")
    protected long booking;

    @Column(unique = false, nullable = false)
    protected String sender;

    @Column(unique = false, nullable = false)
    protected String receiver;

    @Column(unique = true, nullable = true)
    protected Long serviceId;

    public Message() {
    }


    public Message(long id, String body, LocalDateTime date, long booking, String sender, String receiver) {
        this.id = id;
        this.body = body;
        this.date = date;
        this.booking = booking;
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Gets the value of the id property.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBody(String value) {
        this.body = value;
    }

    /**
     * Gets the value of the date property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setDate(LocalDateTime value) {
        this.date = value;
    }

    /**
     * Gets the value of the booking property.
     */
    public long getBooking() {
        return booking;
    }

    /**
     * Sets the value of the booking property.
     */
    public void setBooking(long value) {
        this.booking = value;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
