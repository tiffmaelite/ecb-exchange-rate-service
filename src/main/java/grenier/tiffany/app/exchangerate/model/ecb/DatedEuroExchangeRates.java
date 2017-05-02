package grenier.tiffany.app.exchangerate.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = "Cube")
@XmlAccessorType(XmlAccessType.FIELD)
public final class DatedEuroExchangeRates implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Cube")
    private Collection<EuroExchangeRate> cube;
    @XmlAttribute(name = "time")
    private String time;

    public Collection<EuroExchangeRate> getCube() {
        return cube;
    }

    public String getTime() {
        return time;
    }

    public void setCube(final Collection<EuroExchangeRate> cube) {
        this.cube = cube;
    }

    public void setTime(final String time) {
        this.time = time;
    }
}
