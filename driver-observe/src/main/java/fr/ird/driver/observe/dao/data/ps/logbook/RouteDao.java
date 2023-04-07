package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.data.AbstractDataDao;
import io.ultreia.java4all.util.sql.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class RouteDao extends AbstractDataDao<Route> {


    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " date,\n" +
            /* 08 */ " timeAtSea,\n" +
            /* 09 */ " fishingTime\n" +
            " FROM ps_logbook.Route main WHERE ";
    private static final String BY_PARENT = "main.trip = ? ORDER BY main.date";

    public RouteDao() {
        super(Route.class, Route::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(Route result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setDate(rs.getDate(7));
        result.setTimeAtSea(rs.getInt(8));
        result.setFishingTime(rs.getInt(9));
        result.setActivity(daoSupplier().getPsLogbookActivityDao().lazySetByParentId(result.getTopiaId()));
    }

    public Date firstDate() {
        SqlQuery<Date> query = SqlQuery.wrap("SELECT DISTINCT(r.date) FROM ps_logbook.Route r ORDER BY r.date ASC", rs -> rs.getDate(1));
        return findFirstOrNull(query);

    }

    public Date lastDate() {
        SqlQuery<Date> query = SqlQuery.wrap("SELECT DISTINCT(r.date) FROM ps_logbook.Route r ORDER BY r.date DESC", rs -> rs.getDate(1));
        return findFirstOrNull(query);
    }

}
