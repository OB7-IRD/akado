package fr.ird.driver.observe.dao.referential.ps.localmarket;

import fr.ird.driver.observe.business.referential.ps.localmarket.BatchWeightType;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class BatchWeightTypeDao extends AbstractI18nReferentialDao<BatchWeightType> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_localmarket.BatchWeightType";

    public BatchWeightTypeDao() {
        super(BatchWeightType.class, QUERY, BatchWeightType::new);
    }
}
