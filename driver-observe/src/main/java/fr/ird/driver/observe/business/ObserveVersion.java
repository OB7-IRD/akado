package fr.ird.driver.observe.business;

import io.ultreia.java4all.util.Version;

import java.util.Date;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveVersion {
    public static final String OBSERVE_MODEL_MIN_VERSION = "9.2";
    public static final String OBSERVE_MODEL_MAX_VERSION = "9.2";
    private final Version version;
    private final Date date;

    public ObserveVersion(String version, Date date) {
        if (version == null || version.isEmpty()) {
            throw new IllegalArgumentException("version parameter can not be null nor empty.");
        }
        this.version = Version.valueOf(version);
        this.date = date;
    }

    public Version getVersion() {
        return version;
    }

    public Date getDate() {
        return date;
    }
}
