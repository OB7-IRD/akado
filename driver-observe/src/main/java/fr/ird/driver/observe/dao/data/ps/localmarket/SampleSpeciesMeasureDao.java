package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.business.data.ps.localmarket.SampleSpeciesMeasure;
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
public class SampleSpeciesMeasureDao extends AbstractDataDao<SampleSpeciesMeasure> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " count,\n" +
            /* 07 */ " sizeClass\n" +
            " FROM ps_localmarket.SampleSpeciesMeasure main WHERE ";
    private static final String BY_PARENT = "main.sampleSpecies = ? ORDER BY main.sizeClass";

    public SampleSpeciesMeasureDao() {
        super(SampleSpeciesMeasure.class, SampleSpeciesMeasure::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(SampleSpeciesMeasure result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setCount(rs.getInt(6));
        result.setSizeClass(rs.getDouble(7));
    }
}
