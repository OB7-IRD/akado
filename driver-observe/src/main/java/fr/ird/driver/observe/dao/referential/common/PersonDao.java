package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.Person;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractReferentialDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class PersonDao extends AbstractReferentialDao<Person> {

    private static final String QUERY = REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 10 */ " lastName,\n" +
            /* 11 */ " firstName,\n" +
            /* 12 */ " observer,\n" +
            /* 13 */ " captain,\n" +
            /* 14 */ " dataEntryOperator,\n" +
            /* 15 */ " dataSource,\n" +
            /* 16 */ " psSampler,\n" +
            /* 17 */ " country\n" +
            " FROM common.Person";

    public PersonDao() {
        super(Person.class, QUERY, Person::new);
    }

    @Override
    protected void fill(Person result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setLastName(rs.getString(10));
        result.setFirstName(rs.getString(11));
        result.setObserver(rs.getBoolean(12));
        result.setCaptain(rs.getBoolean(13));
        result.setDataEntryOperator(rs.getBoolean(14));
        result.setDataSource(rs.getBoolean(15));
        result.setPsSampler(rs.getBoolean(16));
        result.setCountry(referentialCache().lazyReferential(Country.class, rs.getString(17)));
    }
}
