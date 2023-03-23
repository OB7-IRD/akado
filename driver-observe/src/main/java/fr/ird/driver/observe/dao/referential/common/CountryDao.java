package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
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
public class CountryDao extends AbstractI18nReferentialDao<Country> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " iso2Code,\n" +
            /* 19 */ " iso3Code\n" +
            " FROM common.Country";

    public CountryDao() {
        super(Country.class, QUERY, Country::new);
    }

    @Override
    protected void fill(Country result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setIso2Code(rs.getString(18));
        result.setIso3Code(rs.getString(19));
    }
}
