package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.SampleType;
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
public class SampleTypeDao extends AbstractI18nReferentialDao<SampleType> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " localMarket,\n" +
            /* 19 */ " logbook,\n" +
            /* 20 */ " startDate,\n" +
            /* 21 */ " endDate\n" +
            " FROM ps_common.SampleType";

    public SampleTypeDao() {
        super(SampleType.class, QUERY, SampleType::new);
    }

    @Override
    protected void fill(SampleType result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setLocalMarket(rs.getBoolean(18));
        result.setLogbook(rs.getBoolean(19));
        result.setStartDate(rs.getDate(20));
        result.setEndDate(rs.getDate(21));
    }
}
