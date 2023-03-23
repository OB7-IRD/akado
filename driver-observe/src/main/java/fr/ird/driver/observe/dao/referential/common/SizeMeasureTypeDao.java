package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SizeMeasureTypeDao extends AbstractI18nReferentialDao<SizeMeasureType> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM common.SizeMeasureType";

    public SizeMeasureTypeDao() {
        super(SizeMeasureType.class, QUERY, SizeMeasureType::new);
    }
}
