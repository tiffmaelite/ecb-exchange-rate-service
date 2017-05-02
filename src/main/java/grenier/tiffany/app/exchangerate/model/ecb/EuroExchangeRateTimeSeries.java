package grenier.tiffany.app.exchangerate.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@XmlRootElement(name = "Cube")
@XmlAccessorType(XmlAccessType.FIELD)
public final class EuroExchangeRateTimeSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Cube")
    private Collection<DatedEuroExchangeRates> cube;

    public Collection<DatedEuroExchangeRates> getCube() {
        return cube;
    }

    public void setCube(final Collection<DatedEuroExchangeRates> cube) {
        this.cube = cube;
    }
}