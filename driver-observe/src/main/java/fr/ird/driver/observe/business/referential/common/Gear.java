package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Gear extends I18nReferentialEntity {

    private Supplier<Set<GearCharacteristic>> gearCharacteristic = SingletonSupplier.of(LinkedHashSet::new);

    public Set<GearCharacteristic> getGearCharacteristic() {
        return gearCharacteristic.get();
    }

    public void setGearCharacteristic(Supplier<Set<GearCharacteristic>> gearCharacteristic) {
        this.gearCharacteristic = Objects.requireNonNull(gearCharacteristic);
    }
}
