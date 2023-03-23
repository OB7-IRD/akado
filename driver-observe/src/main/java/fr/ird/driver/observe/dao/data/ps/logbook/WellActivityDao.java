package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.WellActivity;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.DaoSupplier;
import fr.ird.driver.observe.dao.data.AbstractDataDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellActivityDao extends AbstractDataDao<WellActivity> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " activity\n" +
            " FROM ps_logbook.WellActivity main WHERE ";
    private static final String BY_PARENT = "main.well = ? ORDER BY main.topiaCreateDate";

    public WellActivityDao() {
        super(WellActivity.class, WellActivity::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(WellActivity result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        DaoSupplier daoSupplier = daoSupplier();
        result.setActivity(daoSupplier.getPsLogbookActivityDao().lazyFindById(rs.getString(6)));
        result.setWellActivitySpecies(daoSupplier.getPsLogbookWellActivitySpeciesDao().lazyListByParentId(result.getTopiaId()));
    }
}
