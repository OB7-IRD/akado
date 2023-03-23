package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.ReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VesselSizeCategory extends ReferentialEntity {

    private String capacityLabel;
    private String gaugeLabel;

    public String getCapacityLabel() {
        return capacityLabel;
    }

    public void setCapacityLabel(String capacityLabel) {
        this.capacityLabel = capacityLabel;
    }

    public String getGaugeLabel() {
        return gaugeLabel;
    }

    public void setGaugeLabel(String gaugeLabel) {
        this.gaugeLabel = gaugeLabel;
    }
}
