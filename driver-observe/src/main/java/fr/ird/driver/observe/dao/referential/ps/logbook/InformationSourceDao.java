package fr.ird.driver.observe.dao.referential.ps.logbook;

import fr.ird.driver.observe.business.referential.ps.logbook.InformationSource;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class InformationSourceDao extends AbstractI18nReferentialDao<InformationSource> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_logbook.InformationSource";

    public InformationSourceDao() {
        super(InformationSource.class, QUERY, InformationSource::new);
    }
}
