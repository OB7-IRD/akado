package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.Organism;
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
public class OrganismDao extends AbstractI18nReferentialDao<Organism> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " description,\n" +
            /* 19 */ " country\n" +
            " FROM common.Organism";

    public OrganismDao() {
        super(Organism.class, QUERY, Organism::new);
    }

    @Override
    protected void fill(Organism result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setDescription(rs.getString(18));
        result.setCountry(referentialCache().lazyReferential(Country.class, rs.getString(19)));
    }
}
