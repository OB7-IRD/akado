package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.VesselSizeCategory;
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
public class VesselSizeCategoryDao extends AbstractReferentialDao<VesselSizeCategory> {

    private static final String QUERY = REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 10 */ " capacityLabel,\n" +
            /* 11 */ " gaugeLabel\n" +
            " FROM common.VesselSizeCategory";

    public VesselSizeCategoryDao() {
        super(VesselSizeCategory.class, QUERY, VesselSizeCategory::new);
    }

    @Override
    protected void fill(VesselSizeCategory result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setCapacityLabel(rs.getString(10));
        result.setGaugeLabel(rs.getString(11));
    }
}
