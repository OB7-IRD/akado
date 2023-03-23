package fr.ird.driver.observe;

import fr.ird.driver.observe.business.Entity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created on 19/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Ids {

    public static final Ids INSTANCE = new Ids();

    private final Properties ids;

    public static String get(Class<? extends Entity> entityType) {
        return INSTANCE.ids.getProperty(entityType.getName() + ".id");
    }

    public Ids() {
        this.ids = new Properties();
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("ids.properties")) {
            ids.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
