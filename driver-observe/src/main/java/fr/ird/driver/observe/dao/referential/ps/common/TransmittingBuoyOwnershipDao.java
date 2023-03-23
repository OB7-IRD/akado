package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyOwnership;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TransmittingBuoyOwnershipDao extends AbstractI18nReferentialDao<TransmittingBuoyOwnership> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_common.TransmittingBuoyOwnership";

    public TransmittingBuoyOwnershipDao() {
        super(TransmittingBuoyOwnership.class, QUERY, TransmittingBuoyOwnership::new);
    }
}
