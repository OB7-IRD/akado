package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.data.AbstractDataDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleActivityDao extends AbstractDataDao<SampleActivity> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " weightedWeight,\n" +
            /* 07 */ " activity\n" +
            " FROM ps_logbook.SampleActivity main WHERE ";
    private static final String BY_PARENT = "main.sample = ? ORDER BY main.topiaCreateDate";

    public SampleActivityDao() {
        super(SampleActivity.class, SampleActivity::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(SampleActivity result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setWeightedWeight(rs.getFloat(6));
        result.setActivity(daoSupplier().getPsLogbookActivityDao().lazyFindById(rs.getString(7)));
    }
}
