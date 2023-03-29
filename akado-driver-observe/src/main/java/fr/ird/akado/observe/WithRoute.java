package fr.ird.akado.observe;

import fr.ird.driver.observe.business.data.ps.logbook.Route;

/**
 * Created on 29/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public interface WithRoute extends WithTrip {

    Route getRoute();

    void setRoute(Route route);

    static  <X extends WithRoute> X copy(X target, WithRoute source) {
        WithTrip.copy(target, source);
        target.setRoute(source.getRoute());
        return target;
    }
}
