package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObservedSystemDao extends AbstractI18nReferentialDao<ObservedSystem> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " allowObservation,\n" +
            /* 19 */ " allowLogbook,\n" +
            /* 20 */ " schoolType\n" +
            " FROM ps_common.ObservedSystem";
    private boolean allowObservation;
    private boolean allowLogbook;
    private Supplier<SchoolType> schoolType;

    public ObservedSystemDao() {
        super(ObservedSystem.class, QUERY, ObservedSystem::new);
    }

    @Override
    protected void fill(ObservedSystem result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setAllowObservation(rs.getBoolean(18));
        result.setAllowLogbook(rs.getBoolean(19));
        result.setSchoolType(referentialCache().lazyReferential(SchoolType.class, rs.getString(20)));
    }
}
