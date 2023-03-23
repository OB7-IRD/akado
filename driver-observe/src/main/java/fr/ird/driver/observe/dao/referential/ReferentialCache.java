package fr.ird.driver.observe.dao.referential;

import fr.ird.driver.observe.business.referential.ReferentialEntity;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.DaoSupplier;
import fr.ird.driver.observe.dao.EntityNotFoundException;
import io.ultreia.java4all.lang.Strings;
import io.ultreia.java4all.util.SingletonSupplier;
import io.ultreia.java4all.util.TimeLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Keep all referential in a cache.
 * <p>
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ReferentialCache {
    private static final Logger log = LogManager.getLogger(ReferentialCache.class);
    private final Map<String, Map<String, ? extends ReferentialEntity>> cache;

    public ReferentialCache() {
        this.cache = new TreeMap<>();
    }

    public <E extends ReferentialEntity> E get(Class<E> referentialType, String id) throws EntityNotFoundException {
        if (id == null) {
            return null;
        }
        Map<String, ?> map = cache.get(Objects.requireNonNull(referentialType).getName());
        E result = referentialType.cast(map.get(id));
        if (result == null) {
            throw new EntityNotFoundException(id);
        }
        return result;
    }

    public <E extends ReferentialEntity> List<E> getAll(Class<E> referentialType) {
        @SuppressWarnings("unchecked") Collection<E> values = (Collection<E>) cache().get(referentialType.getName()).values();
        return List.copyOf(values);
    }

    public <E extends ReferentialEntity> List<E> getSome(Class<E> referentialType, Predicate<E> filter) {
        @SuppressWarnings("unchecked") Collection<E> values = (Collection<E>) cache().get(referentialType.getName()).values();
        return values.stream().filter(filter).collect(Collectors.toList());
    }


    public <E extends ReferentialEntity> E getOne(Class<E> referentialType, Predicate<E> filter) {
        @SuppressWarnings("unchecked") Collection<E> values = (Collection<E>) cache().get(referentialType.getName()).values();
        return values.stream().filter(filter).findAny().orElse(null);
    }

    public Map<String, Map<String, ? extends ReferentialEntity>> cache() {
        return Collections.unmodifiableMap(cache);
    }

    public void load(DaoSupplier daoSupplier) throws ObserveDriverException {
        long t0 = TimeLog.getTime();
        daoSupplier.referentialDaoOrder().forEach(this::load);
        log.info("Referential cache loaded (duration: {})", Strings.convertTime(TimeLog.getTime() - t0));
    }

    public <EE extends ReferentialEntity> Supplier<EE> lazyReferential(Class<EE> referentialType, String id) {
        return SingletonSupplier.of(() -> {
            try {
                return get(referentialType, id);
            } catch (EntityNotFoundException e) {
                // this should never happen
                throw new RuntimeException(e);
            }
        }, id != null);
    }

    protected <E extends ReferentialEntity> void load(AbstractReferentialDao<E> dao) throws ObserveDriverException {
        List<E> referentialList = dao.findAll();
        cache.put(dao.entityType().getName(), referentialList.stream().collect(Collectors.toUnmodifiableMap(ReferentialEntity::getTopiaId, Function.identity())));
    }

    public void clear() {
        cache.clear();
    }
}
