package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.ps.common.ObjectOperation;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class FloatingObject extends DataEntity {

    private String comment;
    private String supportVesselName;
    private String computedWhenArrivingBiodegradable;
    private String computedWhenArrivingNonEntangling;
    private String computedWhenArrivingSimplifiedObjectType;
    private String computedWhenLeavingBiodegradable;
    private String computedWhenLeavingNonEntangling;
    private String computedWhenLeavingSimplifiedObjectType;
    private Supplier<ObjectOperation> objectOperation = () -> null;
    private Supplier<Set<TransmittingBuoy>> transmittingBuoy = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<FloatingObjectPart>> floatingObjectPart = SingletonSupplier.of(LinkedHashSet::new);

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSupportVesselName() {
        return supportVesselName;
    }

    public void setSupportVesselName(String supportVesselName) {
        this.supportVesselName = supportVesselName;
    }

    public String getComputedWhenArrivingBiodegradable() {
        return computedWhenArrivingBiodegradable;
    }

    public void setComputedWhenArrivingBiodegradable(String computedWhenArrivingBiodegradable) {
        this.computedWhenArrivingBiodegradable = computedWhenArrivingBiodegradable;
    }

    public String getComputedWhenArrivingNonEntangling() {
        return computedWhenArrivingNonEntangling;
    }

    public void setComputedWhenArrivingNonEntangling(String computedWhenArrivingNonEntangling) {
        this.computedWhenArrivingNonEntangling = computedWhenArrivingNonEntangling;
    }

    public String getComputedWhenArrivingSimplifiedObjectType() {
        return computedWhenArrivingSimplifiedObjectType;
    }

    public void setComputedWhenArrivingSimplifiedObjectType(String computedWhenArrivingSimplifiedObjectType) {
        this.computedWhenArrivingSimplifiedObjectType = computedWhenArrivingSimplifiedObjectType;
    }

    public String getComputedWhenLeavingBiodegradable() {
        return computedWhenLeavingBiodegradable;
    }

    public void setComputedWhenLeavingBiodegradable(String computedWhenLeavingBiodegradable) {
        this.computedWhenLeavingBiodegradable = computedWhenLeavingBiodegradable;
    }

    public String getComputedWhenLeavingNonEntangling() {
        return computedWhenLeavingNonEntangling;
    }

    public void setComputedWhenLeavingNonEntangling(String computedWhenLeavingNonEntangling) {
        this.computedWhenLeavingNonEntangling = computedWhenLeavingNonEntangling;
    }

    public String getComputedWhenLeavingSimplifiedObjectType() {
        return computedWhenLeavingSimplifiedObjectType;
    }

    public void setComputedWhenLeavingSimplifiedObjectType(String computedWhenLeavingSimplifiedObjectType) {
        this.computedWhenLeavingSimplifiedObjectType = computedWhenLeavingSimplifiedObjectType;
    }

    public ObjectOperation getObjectOperation() {
        return objectOperation.get();
    }

    public void setObjectOperation(Supplier<ObjectOperation> objectOperation) {
        this.objectOperation = Objects.requireNonNull(objectOperation);
    }

    public Set<TransmittingBuoy> getTransmittingBuoy() {
        return transmittingBuoy.get();
    }

    public void setTransmittingBuoy(Supplier<Set<TransmittingBuoy>> transmittingBuoy) {
        this.transmittingBuoy = Objects.requireNonNull(transmittingBuoy);
    }

    public Set<FloatingObjectPart> getFloatingObjectPart() {
        return floatingObjectPart.get();
    }

    public void setFloatingObjectPart(Supplier<Set<FloatingObjectPart>> floatingObjectPart) {
        this.floatingObjectPart = Objects.requireNonNull(floatingObjectPart);
    }
}
