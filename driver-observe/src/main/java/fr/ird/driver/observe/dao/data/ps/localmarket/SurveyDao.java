package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.business.data.ps.localmarket.Survey;
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
public class SurveyDao extends AbstractDataDao<Survey> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " date,\n" +
            /* 08 */ " number\n" +
            " FROM ps_localmarket.Survey main WHERE ";
    private static final String BY_PARENT = "main.trip = ? ORDER BY main.date, main.number";

    public SurveyDao() {
        super(Survey.class, Survey::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(Survey result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setDate(rs.getDate(7));
        result.setNumber(rs.getInt(8));
        result.setSurveyPart(daoSupplier().getPsLocalmarketSurveyPartDao().lazySetByParentId(result.getTopiaId()));
    }
}
