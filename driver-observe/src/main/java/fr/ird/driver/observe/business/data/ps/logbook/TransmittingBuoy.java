package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyOperation;
import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyOwnership;
import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyType;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TransmittingBuoy extends DataEntity {

    private String comment;
    private String code;
    private Float latitude;
    private Float longitude;
    private Supplier<TransmittingBuoyOwnership> transmittingBuoyOwnership = () -> null;
    private Supplier<TransmittingBuoyType> transmittingBuoyType = () -> null;
    private Supplier<TransmittingBuoyOperation> transmittingBuoyOperation = () -> null;
    private Supplier<Country> country = () -> null;
    private Supplier<Vessel> vessel = () -> null;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public TransmittingBuoyOwnership getTransmittingBuoyOwnership() {
        return transmittingBuoyOwnership.get();
    }

    public void setTransmittingBuoyOwnership(Supplier<TransmittingBuoyOwnership> transmittingBuoyOwnership) {
        this.transmittingBuoyOwnership = Objects.requireNonNull(transmittingBuoyOwnership);
    }

    public TransmittingBuoyType getTransmittingBuoyType() {
        return transmittingBuoyType.get();
    }

    public void setTransmittingBuoyType(Supplier<TransmittingBuoyType> transmittingBuoyType) {
        this.transmittingBuoyType = Objects.requireNonNull(transmittingBuoyType);
    }

    public TransmittingBuoyOperation getTransmittingBuoyOperation() {
        return transmittingBuoyOperation.get();
    }

    public void setTransmittingBuoyOperation(Supplier<TransmittingBuoyOperation> transmittingBuoyOperation) {
        this.transmittingBuoyOperation = Objects.requireNonNull(transmittingBuoyOperation);
    }

    public Country getCountry() {
        return country.get();
    }

    public void setCountry(Supplier<Country> country) {
        this.country = Objects.requireNonNull(country);
    }

    public Vessel getVessel() {
        return vessel.get();
    }

    public void setVessel(Supplier<Vessel> vessel) {
        this.vessel = Objects.requireNonNull(vessel);
    }
}
