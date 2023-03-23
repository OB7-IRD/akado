package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.Harbour;
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
public class HarbourDao extends AbstractI18nReferentialDao<Harbour> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " locode,\n" +
            /* 19 */ " latitude,\n" +
            /* 20 */ " longitude,\n" +
            /* 21 */ " country\n" +
            " FROM common.Harbour";

    public HarbourDao() {
        super(Harbour.class, QUERY, Harbour::new);
    }

    @Override
    protected void fill(Harbour result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setLocode(rs.getString(18));
        result.setLatitude(getFloat(rs, 19));
        result.setLongitude(getFloat(rs, 20));
        result.setCountry(referentialCache().lazyReferential(Country.class, rs.getString(21)));
    }

}
