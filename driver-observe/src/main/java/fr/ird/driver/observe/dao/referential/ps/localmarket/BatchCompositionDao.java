package fr.ird.driver.observe.dao.referential.ps.localmarket;

import fr.ird.driver.observe.business.referential.ps.localmarket.BatchComposition;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class BatchCompositionDao extends AbstractI18nReferentialDao<BatchComposition> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM ps_localmarket.BatchComposition";

    public BatchCompositionDao() {
        super(BatchComposition.class, QUERY, BatchComposition::new);
    }
}
