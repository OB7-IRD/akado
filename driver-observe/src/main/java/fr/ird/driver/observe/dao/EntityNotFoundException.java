package fr.ird.driver.observe.dao;

import fr.ird.driver.observe.common.ObserveDriverException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class EntityNotFoundException extends ObserveDriverException {
    public EntityNotFoundException(String id) {
        super("Could not find entity with id: " + id);
    }
}
