package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.SizeMeasureMethod;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SizeMeasureDao extends AbstractI18nReferentialDao<SizeMeasureMethod> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM common.SizeMeasureMethod";

    public SizeMeasureDao() {
        super(SizeMeasureMethod.class, QUERY, SizeMeasureMethod::new);
    }
}
