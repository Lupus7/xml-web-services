
package community;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RequestState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PENDING"/&gt;
 *     &lt;enumeration value="RESERVED"/&gt;
 *     &lt;enumeration value="PAID"/&gt;
 *     &lt;enumeration value="CANCELED"/&gt;
 *     &lt;enumeration value="ENDED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "RequestState", namespace = "xml-web-services-cars")
@XmlEnum
public enum RequestState {

    PENDING,
    RESERVED,
    PAID,
    CANCELED,
    ENDED;

    public String value() {
        return name();
    }

    public static RequestState fromValue(String v) {
        return valueOf(v);
    }

}
