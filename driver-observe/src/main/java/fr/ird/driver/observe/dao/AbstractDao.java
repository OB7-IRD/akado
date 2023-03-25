package fr.ird.driver.observe.dao;

import fr.ird.driver.observe.business.Entity;
import fr.ird.driver.observe.business.referential.ReferentialEntity;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.ReferentialCache;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.SingletonSupplier;
import io.ultreia.java4all.util.sql.SqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractDao<E extends Entity> {

    public static final String ASSOCIATION_SQL = "SELECT main.%1$s FROM %2$s.%3$s main WHERE main.%4$s = ?";
    public static final String ASSOCIATION_ORDERED_SQL = "SELECT main.%1$s FROM %2$s.%3$s main WHERE main.%4$s = ? ORDER BY %5$s";
    protected static final String ENTITY_QUERY = "SELECT\n" +
            /* 01 */ " topiaid,\n" +
            /* 02 */ " topiaVersion,\n" +
            /* 03 */ " topiaCreateDate,\n" +
            /* 04 */ " lastUpdateDate,\n" +
            /* 05 */ " homeId";
    protected static final String WHERE_BY_ID_CLAUSE = "main.topiaId = ?";

    /**
     * Entity managed by this dao.
     */
    protected final Class<E> entityType;
    /**
     * To create a new instance of the entity type managed by this dao.
     */
    protected final Supplier<E> instanceCreator;
    /**
     * Supplier of referential cache.
     */
    protected final Supplier<ReferentialCache> referentialCacheSupplier;
    /**
     * Supplier of dao.
     */
    protected final Supplier<DaoSupplier> daoSupplier;
    /**
     * The connection used to access database.
     */
    protected Connection connection;

    public AbstractDao(Class<E> entityType, Supplier<E> instanceCreator) {
        this.entityType = entityType;
        this.instanceCreator = instanceCreator;
        this.connection = ObserveService.getService().getConnection();
        this.referentialCacheSupplier = SingletonSupplier.of(ObserveService.getService()::getReferentialCache);
        this.daoSupplier = SingletonSupplier.of(ObserveService.getService()::getDaoSupplier);
    }

    public Class<E> entityType() {
        return entityType;
    }

    protected E findById(String query, String id) throws ObserveDriverException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    E row = instanceCreator.get();
                    fill(row, rs);
                    return row;
                }
                throw new EntityNotFoundException(id);
            }
        } catch (SQLException e) {
            throw new ObserveDriverException(e);
        }
    }

    public boolean insert(E ignored) {
        throw new UnsupportedOperationException("Not supported on ObServe database.");
    }

    public boolean delete(E ignored) {
        throw new UnsupportedOperationException("Not supported on ObServe database.");
    }

    public boolean update(E ignored) {
        throw new UnsupportedOperationException("Not supported on ObServe database.");
    }

    protected void fill(E result, ResultSet rs) throws SQLException, ObserveDriverException {
        result.setTopiaId(rs.getString(1));
        result.setTopiaVersion(rs.getLong(2));
        result.setTopiaCreateDate(rs.getTimestamp(3));
        result.setLastUpdateDate(rs.getTimestamp(4));
        result.setHomeId(rs.getString(5));
    }

    protected E load(ResultSet rs) throws ObserveDriverException, SQLException {
        E result = instanceCreator.get();
        fill(result, rs);
        return result;
    }

    protected <EE extends ReferentialEntity> Supplier<Set<EE>> lazyReferentialSet(String id, String schemaName, String tableName, String mainPropertyName, String secondPropertyName, Class<EE> secondPropertyType) {
        return SingletonSupplier.of(() -> {
            Set<EE> result = new LinkedHashSet<>();
            String sql = String.format(ASSOCIATION_SQL, secondPropertyName, schemaName, tableName, mainPropertyName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                ReferentialCache referentialCache = referentialCache();
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        EE row = referentialCache.get(secondPropertyType, rs.getString(1));
                        result.add(row);
                    }
                }
            } catch (SQLException ex) {
                throw new ObserveDriverException(ex);
            }
            return result;
        });
    }

    protected Supplier<Set<String>> lazyStringSet(String id, String schemaName, String tableName, String mainPropertyName, String secondPropertyName) {
        return SingletonSupplier.of(() -> {
            Set<String> result = new LinkedHashSet<>();
            String sql = String.format(ASSOCIATION_ORDERED_SQL, secondPropertyName, schemaName, tableName, mainPropertyName, secondPropertyName);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String row = rs.getString(1);
                        result.add(row);
                    }
                }
            } catch (SQLException ex) {
                throw new ObserveDriverException(ex);
            }
            return result;
        });
    }

    protected List<E> findList(SqlQuery<E> query) throws ObserveDriverException {
        List<E> result = new LinkedList<>();
        findCollection(result, query);
        return result;
    }

    protected long count(SqlQuery<Long> query) {
        try (PreparedStatement statement = query.prepareQuery(connection)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return query.prepareResult(resultSet);
            }
        } catch (SQLException e) {
            throw new ObserveDriverException(e);
        }
    }

    protected <O> O findFirstOrNull(SqlQuery<O> query) {
        try (PreparedStatement statement = query.prepareQuery(connection)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return query.prepareResult(resultSet);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new ObserveDriverException(e);
        }
    }

    protected List<E> findList(String query, String parentId) throws ObserveDriverException {
        List<E> result = new LinkedList<>();
        findCollection(result, SqlQuery.wrap(c -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, parentId);
            return statement;
        }, this::load));
        return result;
    }

    protected Set<E> findSet(String query, String parentId) throws ObserveDriverException {
        Set<E> result = new LinkedHashSet<>();
        findCollection(result, SqlQuery.wrap(c -> {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, parentId);
            return statement;
        }, this::load));
        return result;
    }

    protected ReferentialCache referentialCache() {
        return referentialCacheSupplier.get();
    }

    protected DaoSupplier daoSupplier() {
        return daoSupplier.get();
    }

    protected Float getFloat(ResultSet rs, int position) throws SQLException {
        Number object = (Number) rs.getObject(position);
        return object == null ? null : object.floatValue();
    }

    protected Integer getInteger(ResultSet rs, int position) throws SQLException {
        Number object = (Number) rs.getObject(position);
        return object == null ? null : object.intValue();
    }

    private void findCollection(Collection<E> result, SqlQuery<E> query) throws ObserveDriverException {
        try (PreparedStatement statement = query.prepareQuery(connection)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    E row = query.prepareResult(resultSet);
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            throw new ObserveDriverException(e);
        }
    }
}
