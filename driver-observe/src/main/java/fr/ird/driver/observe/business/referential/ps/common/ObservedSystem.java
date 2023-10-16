package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObservedSystem extends I18nReferentialEntity {

    private boolean observation;
    private boolean logbook;
    private Supplier<SchoolType> schoolType = () -> null;

    public SchoolType getSchoolType() {
        return schoolType.get();
    }

    public void setSchoolType(Supplier<SchoolType> schoolType) {
        this.schoolType = Objects.requireNonNull(schoolType);
    }

    public boolean isObservation() {
        return observation;
    }

    public void setObservation(boolean allowObservation) {
        this.observation = allowObservation;
    }

    public boolean isLogbook() {
        return logbook;
    }

    public void setLogbook(boolean allowLogbook) {
        this.logbook = allowLogbook;
    }

    /**
     * @return {@code true} if baits fishing (code 102).
     */
    public boolean isFishingBaits() {
        return "fr.ird.referential.common.ObservedSystem#1464000000000#102".equals(getTopiaId());
    }
}
