package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.WeightMeasureMethod;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WeightMeasureMethodDao extends AbstractI18nReferentialDao<WeightMeasureMethod> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM common.WeightMeasureMethod";

    public WeightMeasureMethodDao() {
        super(WeightMeasureMethod.class, QUERY, WeightMeasureMethod::new);
    }
}
