package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.FpaZone;
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
public class FpaZoneDao extends AbstractI18nReferentialDao<FpaZone> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " startDate,\n" +
            /* 19 */ " endDate,\n" +
            /* 20 */ " country,\n" +
            /* 21 */ " subdivision\n" +
            " FROM common.FpaZone";

    public FpaZoneDao() {
        super(FpaZone.class, QUERY, FpaZone::new);
    }

    @Override
    protected void fill(FpaZone result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setStartDate(rs.getDate(18));
        result.setEndDate(rs.getDate(19));
        result.setCountry(referentialCache().lazyReferential(Country.class, rs.getString(20)));
        result.setSubdivision(rs.getString(21));
    }
}
