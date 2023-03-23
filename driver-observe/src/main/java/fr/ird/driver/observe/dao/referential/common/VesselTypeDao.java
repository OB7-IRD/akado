package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.VesselType;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VesselTypeDao extends AbstractI18nReferentialDao<VesselType> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM common.VesselType";

    public VesselTypeDao() {
        super(VesselType.class, QUERY, VesselType::new);
    }
}
