package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Wind;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WindDao extends AbstractI18nReferentialDao<Wind> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " minSpeed,\n" +
            /* 19 */ " maxSpeed,\n" +
            /* 20 */ " minSwellHeight,\n" +
            /* 21 */ " maxSwellHeight\n" +
            " FROM common.Wind";

    public WindDao() {
        super(Wind.class, QUERY, Wind::new);
    }

    @Override
    protected void fill(Wind result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setMinSpeed(getInteger(rs, 18));
        result.setMaxSpeed(getInteger(rs, 19));
        result.setMinSwellHeight(getFloat(rs, 20));
        result.setMaxSwellHeight(getFloat(rs, 21));
    }
}
