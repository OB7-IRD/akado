package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.SpeciesFate;
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
public class SpeciesFateDao extends AbstractI18nReferentialDao<SpeciesFate> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " discard,\n" +
            /* 19 */ " weightRangeAllowed\n" +
            " FROM ps_common.SpeciesFate";

    public SpeciesFateDao() {
        super(SpeciesFate.class, QUERY, SpeciesFate::new);
    }

    @Override
    protected void fill(SpeciesFate result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setDiscard((Boolean) rs.getObject(18));
        result.setWeightRangeAllowed(rs.getBoolean(19));
    }
}
