package fr.ird.akado.observe;

import fr.ird.driver.observe.business.data.ps.common.Trip;

/**
 * Created on 29/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public interface WithTrip {

    Trip getTrip();

    void setTrip(Trip trip);

    static <X extends WithTrip> X copy(X target, WithTrip source) {
        target.setTrip(source.getTrip());
        return target;
    }
}
