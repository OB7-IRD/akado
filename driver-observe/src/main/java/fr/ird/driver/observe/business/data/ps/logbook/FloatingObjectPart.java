package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.ps.common.ObjectMaterial;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class FloatingObjectPart extends DataEntity {

    private String whenArriving;
    private String whenLeaving;
    private Supplier<ObjectMaterial> objectMaterial = () -> null;

    public String getWhenArriving() {
        return whenArriving;
    }

    public void setWhenArriving(String whenArriving) {
        this.whenArriving = whenArriving;
    }

    public String getWhenLeaving() {
        return whenLeaving;
    }

    public void setWhenLeaving(String whenLeaving) {
        this.whenLeaving = whenLeaving;
    }

    public ObjectMaterial getObjectMaterial() {
        return objectMaterial.get();
    }

    public void setObjectMaterial(Supplier<ObjectMaterial> objectMaterial) {
        this.objectMaterial = Objects.requireNonNull(objectMaterial);
    }
}
