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

    private boolean allowObservation;
    private boolean allowLogbook;
    private Supplier<SchoolType> schoolType = () -> null;

    public SchoolType getSchoolType() {
        return schoolType.get();
    }

    public void setSchoolType(Supplier<SchoolType> schoolType) {
        this.schoolType = Objects.requireNonNull(schoolType);
    }

    public boolean isAllowObservation() {
        return allowObservation;
    }

    public void setAllowObservation(boolean allowObservation) {
        this.allowObservation = allowObservation;
    }

    public boolean isAllowLogbook() {
        return allowLogbook;
    }

    public void setAllowLogbook(boolean allowLogbook) {
        this.allowLogbook = allowLogbook;
    }

    /**
     * @return {@code true} if baits fishing (code 102).
     */
    public boolean isFishingBaits() {
        return "fr.ird.referential.common.ObservedSystem#1464000000000#102".equals(getTopiaId());
    }
}
