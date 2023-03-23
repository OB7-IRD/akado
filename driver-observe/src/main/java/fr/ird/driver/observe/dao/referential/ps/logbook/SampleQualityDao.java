package fr.ird.driver.observe.dao.referential.ps.logbook;

import fr.ird.driver.observe.business.referential.ps.logbook.SampleQuality;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleQualityDao extends AbstractI18nReferentialDao<SampleQuality> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_logbook.SampleQuality";

    public SampleQualityDao() {
        super(SampleQuality.class, QUERY, SampleQuality::new);
    }
}

