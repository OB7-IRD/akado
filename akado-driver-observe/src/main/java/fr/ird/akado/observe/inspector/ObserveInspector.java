package fr.ird.akado.observe.inspector;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.result.Results;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

/**
 * Parent of any inspector for ObServe.
 * <p>
 * We will use then {@link java.util.ServiceLoader} mechanism on inherited inspectors to find them.
 * <p>
 * Created on 20/03/2023.
 *
 * @param <T> the type of inspection
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveInspector<T> extends Inspector<T> {

    protected static <T, I extends ObserveInspector<T>> List<I> loadInspectors(Class<I> inspectorType) {
        List<I> result = new LinkedList<>();
        for (I inspector : ServiceLoader.load(inspectorType)) {
            result.add(inspector);
        }
        return result;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected static <T, I extends ObserveInspector<T>> List<I> filterInspectors(Class<I> inspectorType, List<Inspector<?>> inspectors) {
        return (List) inspectors.stream().filter(i -> inspectorType.isAssignableFrom(i.getClass())).collect(Collectors.toList());
    }

    @Override
    public abstract Results execute() throws Exception;
}
