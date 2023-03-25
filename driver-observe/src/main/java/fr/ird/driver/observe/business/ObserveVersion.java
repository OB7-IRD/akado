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
    /**
     * The only ObServe database version we are using.
     */
    public static final Version VERSION_OBSERVE_COMPATIBILITY = Version.create("9.1").build();
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
