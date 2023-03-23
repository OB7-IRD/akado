package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Sex;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SexDao extends AbstractI18nReferentialDao<Sex> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM common.Sex";

    public SexDao() {
        super(Sex.class, QUERY, Sex::new);
    }
}
