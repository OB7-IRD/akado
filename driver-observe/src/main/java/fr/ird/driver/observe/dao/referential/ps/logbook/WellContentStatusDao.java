package fr.ird.driver.observe.dao.referential.ps.logbook;

import fr.ird.driver.observe.business.referential.ps.logbook.WellContentStatus;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellContentStatusDao extends AbstractI18nReferentialDao<WellContentStatus> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_logbook.WellContentStatus";

    public WellContentStatusDao() {
        super(WellContentStatus.class, QUERY, WellContentStatus::new);
    }
}
