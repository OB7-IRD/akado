package fr.ird.driver.observe.dao.data.ps.localmarket;

import fr.ird.driver.observe.business.data.ps.localmarket.SurveyPart;
import fr.ird.driver.observe.business.referential.common.Species;
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
public class SurveyPartDao extends AbstractDataDao<SurveyPart> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " species,\n" +
            /* 07 */ " proportion\n" +
            " FROM ps_localmarket.SurveyPart main WHERE ";
    private static final String BY_PARENT = "main.survey = ? ORDER BY main.topiaCreateDate";

    public SurveyPartDao() {
        super(SurveyPart.class, SurveyPart::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(SurveyPart result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setSpecies(referentialCache().lazyReferential(Species.class, rs.getString(6)));
        result.setProportion(rs.getInt(7));
    }
}
