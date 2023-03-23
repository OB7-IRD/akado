package fr.ird.driver.observe.dao.referential.ps.landing;

import fr.ird.driver.observe.business.referential.ps.landing.Destination;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class DestinationDao extends AbstractI18nReferentialDao<Destination> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_landing.Destination";

    public DestinationDao() {
        super(Destination.class, QUERY, Destination::new);
    }
}
