package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.AcquisitionStatus;
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
public class AcquisitionStatusDao extends AbstractI18nReferentialDao<AcquisitionStatus> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " observation,\n" +
            /* 19 */ " logbook,\n" +
            /* 20 */ " landing,\n" +
            /* 21 */ " targetWellsSampling,\n" +
            /* 22 */ " localMarket,\n" +
            /* 23 */ " localMarketWellsSampling,\n" +
            /* 24 */ " localMarketSurveySampling,\n" +
            /* 25 */ " advancedSampling,\n" +
            /* 26 */ " fieldEnabler\n" +
            " FROM ps_common.AcquisitionStatus";

    public AcquisitionStatusDao() {
        super(AcquisitionStatus.class, QUERY, AcquisitionStatus::new);
    }

    @Override
    protected void fill(AcquisitionStatus result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setObservation(rs.getBoolean(18));
        result.setLogbook(rs.getBoolean(19));
        result.setLanding(rs.getBoolean(20));
        result.setTargetWellsSampling(rs.getBoolean(21));
        result.setLocalMarket(rs.getBoolean(22));
        result.setLocalMarketWellsSampling(rs.getBoolean(23));
        result.setLocalMarketSurveySampling(rs.getBoolean(24));
        result.setAdvancedSampling(rs.getBoolean(25));
        result.setFieldEnabler(rs.getBoolean(26));
    }
}
