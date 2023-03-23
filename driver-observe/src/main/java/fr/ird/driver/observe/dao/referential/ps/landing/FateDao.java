package fr.ird.driver.observe.dao.referential.ps.landing;

import fr.ird.driver.observe.business.referential.ps.landing.Fate;
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
public class FateDao extends AbstractI18nReferentialDao<Fate> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " discard\n" +
            " FROM ps_landing.Fate";

    public FateDao() {
        super(Fate.class, QUERY, Fate::new);
    }

    @Override
    protected void fill(Fate result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setDiscard(rs.getBoolean(18));
    }
}
