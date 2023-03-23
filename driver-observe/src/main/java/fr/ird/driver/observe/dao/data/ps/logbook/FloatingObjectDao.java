package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.FloatingObject;
import fr.ird.driver.observe.business.referential.ps.common.ObjectOperation;
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
public class FloatingObjectDao extends AbstractDataDao<FloatingObject> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " supportVesselName,\n" +
            /* 08 */ " computedWhenArrivingBiodegradable,\n" +
            /* 09 */ " computedWhenArrivingNonEntangling,\n" +
            /* 10 */ " computedWhenArrivingSimplifiedObjectType,\n" +
            /* 11 */ " computedWhenLeavingBiodegradable,\n" +
            /* 12 */ " computedWhenLeavingNonEntangling,\n" +
            /* 13 */ " computedWhenLeavingSimplifiedObjectType,\n" +
            /* 14 */ " objectOperation\n" +
            " FROM ps_logbook.FloatingObject main WHERE ";
    private static final String BY_PARENT = "main.activity = ? ORDER BY main.topiaCreateDate";

    public FloatingObjectDao() {
        super(FloatingObject.class, FloatingObject::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(FloatingObject result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setSupportVesselName(rs.getString(7));
        result.setComputedWhenArrivingBiodegradable(rs.getString(8));
        result.setComputedWhenArrivingNonEntangling(rs.getString(9));
        result.setComputedWhenArrivingSimplifiedObjectType(rs.getString(10));
        result.setComputedWhenLeavingBiodegradable(rs.getString(11));
        result.setComputedWhenLeavingNonEntangling(rs.getString(12));
        result.setComputedWhenLeavingSimplifiedObjectType(rs.getString(13));
        result.setObjectOperation(referentialCache().lazyReferential(ObjectOperation.class, rs.getString(14)));
        DaoSupplier daoSupplier = daoSupplier();
        String floatingObjectId = result.getTopiaId();
        result.setTransmittingBuoy(daoSupplier.getPsLogbookTransmittingBuoyDao().lazySetByParentId(floatingObjectId));
        result.setFloatingObjectPart(daoSupplier.getPsLogbookFloatingObjectPartDao().lazySetByParentId(floatingObjectId));
    }
}
