package fr.ird.driver.observe.dao.referential;

import fr.ird.driver.observe.business.referential.ReferentialEntity;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.AbstractDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractReferentialDao<E extends ReferentialEntity> extends AbstractDao<E> {

    protected static final String REFERENTIAL_ENTITY_QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " code,\n" +
            /* 07 */ " uri,\n" +
            /* 08 */ " needComment,\n" +
            /* 09 */ " status";

    private static final Logger log = LogManager.getLogger(AbstractReferentialDao.class);
    protected final String mainQuery;

    public AbstractReferentialDao(Class<E> entityType, String mainQuery, Supplier<E> instanceCreator) {
        super(entityType, instanceCreator);
        this.mainQuery = mainQuery;
    }

    public List<E> findAll() throws ObserveDriverException {
        List<E> result = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(mainQuery)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    E row = load(rs);
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            throw new ObserveDriverException(e);
        }
        log.debug("Loaded {} referential(s) of type: {}", result.size(), entityType().getName());
        return result;
    }

    @Override
    protected void fill(E result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setCode(rs.getString(6));
        result.setUri(rs.getString(7));
        result.setNeedComment(rs.getBoolean(8));
        result.setStatus(rs.getInt(9));
    }

}
