package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TransmittingBuoyOperation extends I18nReferentialEntity {

    /**
     * @return {@code true} if operation is end of signal (code 4)
     */
    public boolean isLost() {
        return "fr.ird.referential.ps.common.TransmittingBuoyOperation#1464000000000#4".equals(getTopiaId());
    }

    /**
     * @return {@code true} if operation is end of use (code 5)
     */
    public boolean isEndOfUse() {
        return "fr.ird.referential.ps.common.TransmittingBuoyOperation#1464000000000#5".equals(getTopiaId());
    }
}
