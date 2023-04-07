package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Set;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VesselActivity extends I18nReferentialEntity {
    public static final Set<String> TRANSSHIPMENT_VESSEL_ACTIVITY_ID = Set.of(
            //TRANSBORDEMENT_VERS_SENNEUR
            "fr.ird.referential.ps.common.VesselActivity#1464000000000#27",
            // TRANSBORDEMENT
            "fr.ird.referential.ps.common.VesselActivity#1464000000000#25",
            // TRANSBORDEMENT_VERS_CANNEUR
            "fr.ird.referential.ps.common.VesselActivity#1464000000000#29");

    private boolean allowFad;

    public boolean isAllowFad() {
        return allowFad;
    }

    public void setAllowFad(boolean allowFad) {
        this.allowFad = allowFad;
    }

    public int getCodeAsInt() {
        return Integer.parseInt(getCode());
    }

    /**
     * @return {@code true} if is fishing (code 6)
     */
    public boolean isFishing() {
        return "fr.ird.referential.ps.common.VesselActivity#1239832675369#0.12552908048322586".equals(getTopiaId());
    }

    /**
     * @return {@code true} if is FAD operation (code 13)
     */
    public boolean isPureFadOperation() {
        return "fr.ird.referential.ps.common.VesselActivity#1239832675370#0.9125190289998782".equals(getTopiaId());
    }

    /**
     * @return {@code true} if vessel activity is a transshipment
     */
    public boolean isTransshipment() {
        return TRANSSHIPMENT_VESSEL_ACTIVITY_ID.contains(getTopiaId());
    }

    /**
     * @return {@code true} if vessel activity is a bait fishing (code 23)
     */
    public boolean isBait() {
        return "fr.ird.referential.ps.common.VesselActivity#1464000000000#23".equals(getTopiaId());
    }
}
