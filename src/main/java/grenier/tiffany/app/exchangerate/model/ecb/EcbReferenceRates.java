package grenier.tiffany.app.exchangerate.model.ecb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "Envelope", namespace = "http://www.gesmes.org/xml/2002-08-01")
@XmlAccessorType(XmlAccessType.FIELD)
public final class EcbReferenceRates implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "Cube")
    private EuroExchangeRateTimeSeries euroExchangeRateTimeSeries;

    public EuroExchangeRateTimeSeries getEuroExchangeRateTimeSeries() {
        return euroExchangeRateTimeSeries;
    }

    public void setEuroExchangeRateTimeSeries(final EuroExchangeRateTimeSeries euroExchangeRateTimeSeries) {
        this.euroExchangeRateTimeSeries = euroExchangeRateTimeSeries;
    }
}
