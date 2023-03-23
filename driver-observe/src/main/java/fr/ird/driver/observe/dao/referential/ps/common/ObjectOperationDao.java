package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.ObjectOperation;
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
public class ObjectOperationDao extends AbstractI18nReferentialDao<ObjectOperation> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " whenArriving,\n" +
            /* 19 */ " whenLeaving\n" +
            " FROM ps_common.ObjectOperation";

    public ObjectOperationDao() {
        super(ObjectOperation.class, QUERY, ObjectOperation::new);
    }

    @Override
    protected void fill(ObjectOperation result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setWhenArriving(rs.getBoolean(18));
        result.setWhenLeaving(rs.getBoolean(19));
    }
}
