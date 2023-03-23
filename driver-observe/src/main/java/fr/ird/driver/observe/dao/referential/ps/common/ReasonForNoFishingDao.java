package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.ReasonForNoFishing;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ReasonForNoFishingDao extends AbstractI18nReferentialDao<ReasonForNoFishing> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_common.ReasonForNoFishing";

    public ReasonForNoFishingDao() {
        super(ReasonForNoFishing.class, QUERY, ReasonForNoFishing::new);
    }
}
