package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Ocean;
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
public class OceanDao extends AbstractI18nReferentialDao<Ocean> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " northEastAllowed,\n" +
            /* 19 */ " southEastAllowed,\n" +
            /* 20 */ " southWestAllowed,\n" +
            /* 21 */ " northWestAllowed\n" +
            " FROM common.Ocean";

    public OceanDao() {
        super(Ocean.class, QUERY, Ocean::new);
    }

    @Override
    protected void fill(Ocean result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setNorthEastAllowed(rs.getBoolean(18));
        result.setSouthEastAllowed(rs.getBoolean(19));
        result.setSouthWestAllowed(rs.getBoolean(20));
        result.setNorthWestAllowed(rs.getBoolean(21));
    }
}
