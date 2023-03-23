package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.common.Organism;
import fr.ird.driver.observe.business.referential.ps.common.Program;
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
public class ProgramDao extends AbstractI18nReferentialDao<Program> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " startDate,\n" +
            /* 19 */ " endDate,\n" +
            /* 20 */ " comment,\n" +
            /* 21 */ " observation,\n" +
            /* 22 */ " logbook,\n" +
            /* 23 */ " organism\n" +
            " FROM ps_common.Program";

    public ProgramDao() {
        super(Program.class, QUERY, Program::new);
    }

    @Override
    protected void fill(Program result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setStartDate(rs.getDate(18));
        result.setEndDate(rs.getDate(19));
        result.setComment(rs.getString(20));
        result.setObservation(rs.getBoolean(21));
        result.setLogbook(rs.getBoolean(22));
        result.setOrganism(referentialCache().lazyReferential(Organism.class, rs.getString(23)));
    }
}
