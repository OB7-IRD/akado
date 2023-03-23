package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.VesselActivity;
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
public class VesselActivityDao extends AbstractI18nReferentialDao<VesselActivity> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " allowFad\n" +
            " FROM ps_common.VesselActivity";

    public VesselActivityDao() {
        super(VesselActivity.class, QUERY, VesselActivity::new);
    }

    @Override
    protected void fill(VesselActivity result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setAllowFad(rs.getBoolean(18));
    }
}
