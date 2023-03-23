package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.FloatingObjectPart;
import fr.ird.driver.observe.business.referential.ps.common.ObjectMaterial;
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
public class FloatingObjectPartDao extends AbstractDataDao<FloatingObjectPart> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " whenArriving,\n" +
            /* 07 */ " whenLeaving,\n" +
            /* 08 */ " objectMaterial\n" +
            " FROM ps_logbook.FloatingObjectPart main WHERE ";
    private static final String BY_PARENT = "main.floatingObject = ? ORDER BY main.topiaCreateDate";

    public FloatingObjectPartDao() {
        super(FloatingObjectPart.class, FloatingObjectPart::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(FloatingObjectPart result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setWhenArriving(rs.getString(6));
        result.setWhenLeaving(rs.getString(7));
        result.setObjectMaterial(referentialCache().lazyReferential(ObjectMaterial.class, rs.getString(8)));
    }
}
