package grenier.tiffany.app.exchangerate.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Cube")
@XmlAccessorType(XmlAccessType.FIELD)
public final class EuroExchangeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "currency")
    private String currency;
    @XmlAttribute(name = "rate")
    private double rate;

    public String getCurrency() {
        return currency;
    }

    public double getRate() {
        return rate;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }
}
