package fr.ird.driver.observe.common;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveDriverException extends RuntimeException {
    public ObserveDriverException(String message) {
        super(message);
    }

    public ObserveDriverException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObserveDriverException(Throwable cause) {
        super(cause);
    }
}
