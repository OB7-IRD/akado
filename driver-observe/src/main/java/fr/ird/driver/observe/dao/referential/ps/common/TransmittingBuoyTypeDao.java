package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyType;
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
public class TransmittingBuoyTypeDao extends AbstractI18nReferentialDao<TransmittingBuoyType> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " technology\n" +
            " FROM ps_common.TransmittingBuoyType";

    public TransmittingBuoyTypeDao() {
        super(TransmittingBuoyType.class, QUERY, TransmittingBuoyType::new);
    }

    @Override
    protected void fill(TransmittingBuoyType result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setTechnology(rs.getString(18));
    }
}
