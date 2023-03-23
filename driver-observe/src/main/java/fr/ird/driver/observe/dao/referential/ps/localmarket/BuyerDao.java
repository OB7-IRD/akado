package fr.ird.driver.observe.dao.referential.ps.localmarket;

import fr.ird.driver.observe.business.referential.common.Harbour;
import fr.ird.driver.observe.business.referential.ps.localmarket.Buyer;
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
public class BuyerDao extends AbstractI18nReferentialDao<Buyer> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " email,\n" +
            /* 19 */ " phone,\n" +
            /* 20 */ " address,\n" +
            /* 21 */ " harbour\n" +
            " FROM ps_localmarket.Buyer";

    public BuyerDao() {
        super(Buyer.class, QUERY, Buyer::new);
    }

    @Override
    protected void fill(Buyer result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setEmail(rs.getString(18));
        result.setPhone(rs.getString(19));
        result.setAddress(rs.getString(20));
        result.setHarbour(referentialCache().lazyReferential(Harbour.class, rs.getString(21)));
    }
}
