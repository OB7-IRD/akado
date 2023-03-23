package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.ShipOwner;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractReferentialDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ShipOwnerDao extends AbstractReferentialDao<ShipOwner> {

    private static final String QUERY = REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 10 */ " label,\n" +
            /* 11 */ " startDate,\n" +
            /* 12 */ " endDate,\n" +
            /* 13 */ " country\n" +
            " FROM common.ShipOwner";

    public ShipOwnerDao() {
        super(ShipOwner.class, QUERY, ShipOwner::new);
    }

    @Override
    protected void fill(ShipOwner result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setLabel(rs.getString(10));
        result.setStartDate(rs.getDate(11));
        result.setEndDate(rs.getDate(12));
        result.setCountry(referentialCache().lazyReferential(Country.class, rs.getString(13)));
    }
}
