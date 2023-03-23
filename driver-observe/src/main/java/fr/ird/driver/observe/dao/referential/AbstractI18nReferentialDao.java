package fr.ird.driver.observe.dao.referential;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
import fr.ird.driver.observe.common.ObserveDriverException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractI18nReferentialDao<E extends I18nReferentialEntity> extends AbstractReferentialDao<E> {

    protected static final String I18N_REFERENTIAL_ENTITY_QUERY = REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 10 */ " label1,\n" +
            /* 11 */ " label2,\n" +
            /* 12 */ " label3,\n" +
            /* 13 */ " label4,\n" +
            /* 14 */ " label5,\n" +
            /* 15 */ " label6,\n" +
            /* 16 */ " label7,\n" +
            /* 17 */ " label8";

    public AbstractI18nReferentialDao(Class<E> entityType, String mainQuery, Supplier<E> instanceCreator) {
        super(entityType, mainQuery, instanceCreator);
    }

    @Override
    protected void fill(E result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setLabel1(rs.getString(10));
        result.setLabel2(rs.getString(11));
        result.setLabel3(rs.getString(12));
        result.setLabel4(rs.getString(13));
        result.setLabel5(rs.getString(14));
        result.setLabel6(rs.getString(15));
        result.setLabel7(rs.getString(16));
        result.setLabel8(rs.getString(17));
    }
}
