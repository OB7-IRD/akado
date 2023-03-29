package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.DataQuality;
import fr.ird.driver.observe.business.referential.common.FpaZone;
import fr.ird.driver.observe.business.referential.common.Wind;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.ReasonForNoFishing;
import fr.ird.driver.observe.business.referential.ps.common.ReasonForNullSet;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;
import fr.ird.driver.observe.business.referential.ps.common.VesselActivity;
import fr.ird.driver.observe.business.referential.ps.logbook.InformationSource;
import fr.ird.driver.observe.business.referential.ps.logbook.SetSuccessStatus;
import fr.ird.driver.observe.common.ObserveUtils;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Activity extends DataEntity {

    private String comment;
    private Date time;
    private Float latitude;
    private Float longitude;
    private Float latitudeOriginal;
    private Float longitudeOriginal;
    private boolean originalDataModified;
    private boolean vmsDivergent;
    private boolean positionCorrected;
    private int number;
    private Integer setCount;
    private Float seaSurfaceTemperature;
    private Integer windDirection;
    private Float totalWeight;
    private Float currentSpeed;
    private Integer currentDirection;
    private Supplier<VesselActivity> vesselActivity = () -> null;
    private Supplier<Wind> wind = () -> null;
    private Supplier<SchoolType> schoolType = () -> null;
    private Supplier<FpaZone> fpaZone = () -> null;
    private Supplier<DataQuality> dataQuality = () -> null;
    private Supplier<InformationSource> informationSource = () -> null;
    private Supplier<ReasonForNoFishing> reasonForNoFishing = () -> null;
    private Supplier<SetSuccessStatus> setSuccessStatus = () -> null;
    private Supplier<ReasonForNullSet> reasonForNullSet = () -> null;
    private Supplier<List<Catch>> catches = SingletonSupplier.of(LinkedList::new);
    private Supplier<Set<FloatingObject>> floatingObject = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<ObservedSystem>> observedSystem = SingletonSupplier.of(LinkedHashSet::new);

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Float getLatitudeOriginal() {
        return latitudeOriginal;
    }

    public void setLatitudeOriginal(Float latitudeOriginal) {
        this.latitudeOriginal = latitudeOriginal;
    }

    public Float getLongitudeOriginal() {
        return longitudeOriginal;
    }

    public void setLongitudeOriginal(Float longitudeOriginal) {
        this.longitudeOriginal = longitudeOriginal;
    }

    public boolean isOriginalDataModified() {
        return originalDataModified;
    }

    public void setOriginalDataModified(boolean originalDataModified) {
        this.originalDataModified = originalDataModified;
    }

    public boolean isVmsDivergent() {
        return vmsDivergent;
    }

    public void setVmsDivergent(boolean vmsDivergent) {
        this.vmsDivergent = vmsDivergent;
    }

    public boolean isPositionCorrected() {
        return positionCorrected;
    }

    public void setPositionCorrected(boolean positionCorrected) {
        this.positionCorrected = positionCorrected;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getSetCount() {
        return setCount;
    }

    public void setSetCount(Integer setCount) {
        this.setCount = setCount;
    }

    public Float getSeaSurfaceTemperature() {
        return seaSurfaceTemperature;
    }

    public void setSeaSurfaceTemperature(Float seaSurfaceTemperature) {
        this.seaSurfaceTemperature = seaSurfaceTemperature;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public Float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(Float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public Integer getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Integer currentDirection) {
        this.currentDirection = currentDirection;
    }

    public VesselActivity getVesselActivity() {
        return vesselActivity.get();
    }

    public void setVesselActivity(Supplier<VesselActivity> vesselActivity) {
        this.vesselActivity = Objects.requireNonNull(vesselActivity);
    }

    public Wind getWind() {
        return wind.get();
    }

    public void setWind(Supplier<Wind> wind) {
        this.wind = Objects.requireNonNull(wind);
    }

    public SchoolType getSchoolType() {
        return schoolType.get();
    }

    public void setSchoolType(Supplier<SchoolType> schoolType) {
        this.schoolType = Objects.requireNonNull(schoolType);
    }

    public FpaZone getFpaZone() {
        return fpaZone.get();
    }

    public void setFpaZone(Supplier<FpaZone> fpaZone) {
        this.fpaZone = Objects.requireNonNull(fpaZone);
    }

    public DataQuality getDataQuality() {
        return dataQuality.get();
    }

    public void setDataQuality(Supplier<DataQuality> dataQuality) {
        this.dataQuality = Objects.requireNonNull(dataQuality);
    }

    public InformationSource getInformationSource() {
        return informationSource.get();
    }

    public void setInformationSource(Supplier<InformationSource> informationSource) {
        this.informationSource = Objects.requireNonNull(informationSource);
    }

    public ReasonForNoFishing getReasonForNoFishing() {
        return reasonForNoFishing.get();
    }

    public void setReasonForNoFishing(Supplier<ReasonForNoFishing> reasonForNoFishing) {
        this.reasonForNoFishing = Objects.requireNonNull(reasonForNoFishing);
    }

    public SetSuccessStatus getSetSuccessStatus() {
        return setSuccessStatus.get();
    }

    public void setSetSuccessStatus(Supplier<SetSuccessStatus> setSuccessStatus) {
        this.setSuccessStatus = Objects.requireNonNull(setSuccessStatus);
    }

    public ReasonForNullSet getReasonForNullSet() {
        return reasonForNullSet.get();
    }

    public void setReasonForNullSet(Supplier<ReasonForNullSet> reasonForNullSet) {
        this.reasonForNullSet = Objects.requireNonNull(reasonForNullSet);
    }

    public List<Catch> getCatches() {
        return catches.get();
    }

    public void setCatches(Supplier<List<Catch>> catches) {
        this.catches = Objects.requireNonNull(catches);
    }

    public Set<FloatingObject> getFloatingObject() {
        return floatingObject.get();
    }

    public void setFloatingObject(Supplier<Set<FloatingObject>> floatingObject) {
        this.floatingObject = Objects.requireNonNull(floatingObject);
    }

    public Set<ObservedSystem> getObservedSystem() {
        return observedSystem.get();
    }

    public void setObservedSystem(Supplier<Set<ObservedSystem>> observedSystem) {
        this.observedSystem = Objects.requireNonNull(observedSystem);
    }

    public int getQuadrant() {
        return ObserveUtils.getQuadrant(getLongitude(), getLatitude());
    }
}
