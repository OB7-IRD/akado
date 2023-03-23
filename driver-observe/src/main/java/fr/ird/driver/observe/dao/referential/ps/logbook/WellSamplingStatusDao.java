package fr.ird.driver.observe.dao.referential.ps.logbook;

import fr.ird.driver.observe.business.referential.ps.logbook.WellSamplingStatus;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellSamplingStatusDao extends AbstractI18nReferentialDao<WellSamplingStatus> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_logbook.WellSamplingStatus";

    public WellSamplingStatusDao() {
        super(WellSamplingStatus.class, QUERY, WellSamplingStatus::new);
    }
}
