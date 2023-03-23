package fr.ird.akado.observe.inspector;

import fr.ird.akado.core.Inspector;

/**
 * Parent of any inspector for ObServe.
 * <p>
 * We will use then {@link java.util.ServiceLoader} mechanism on derivated inspectors to find them.
 * <p>
 * Created on 20/03/2023.
 *
 * @param <T> the type of inspection
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveInspector<T> extends Inspector<T> {
}
