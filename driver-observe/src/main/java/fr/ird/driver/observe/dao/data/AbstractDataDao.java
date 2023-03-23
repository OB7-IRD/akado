package fr.ird.driver.observe.dao.data;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.AbstractDao;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class AbstractDataDao<E extends DataEntity> extends AbstractDao<E> {

    protected final String queryBase;
    private final String byParentWhereClause;

    public AbstractDataDao(Class<E> entityType, Supplier<E> instanceCreator, String queryBase, String byParentWhereClause) {
        super(entityType, instanceCreator);
        this.queryBase = queryBase;
        this.byParentWhereClause = byParentWhereClause;
    }

    public Supplier<E> lazyFindById(String id) throws ObserveDriverException {
        return SingletonSupplier.of(() -> findById(id));
    }

    public E findById(String id) {
        return findById(queryBase + WHERE_BY_ID_CLAUSE, id);
    }

    public Supplier<List<E>> lazyListByParentId(String parentId) throws ObserveDriverException {
        return SingletonSupplier.of(() -> findListByParentId(parentId));
    }

    public Supplier<Set<E>> lazySetByParentId(String parentId) throws ObserveDriverException {
        return SingletonSupplier.of(() -> findSetByParentId(parentId));
    }

    protected List<E> findListByParentId(String parentId) throws ObserveDriverException {
        return findList(queryBase + Objects.requireNonNull(byParentWhereClause), Objects.requireNonNull(parentId));
    }

    protected Set<E> findSetByParentId(String parentId) throws ObserveDriverException {
        return findSet(queryBase + Objects.requireNonNull(byParentWhereClause), Objects.requireNonNull(parentId));
    }
}
