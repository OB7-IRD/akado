package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class GearCharacteristic extends I18nReferentialEntity {

    private String unit;
    private Supplier<GearCharacteristicType> gearCharacteristicType = () -> null;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public GearCharacteristicType getGearCharacteristicType() {
        return gearCharacteristicType.get();
    }

    public void setGearCharacteristicType(Supplier<GearCharacteristicType> gearCharacteristicType) {
        this.gearCharacteristicType = Objects.requireNonNull(gearCharacteristicType);
    }
}
