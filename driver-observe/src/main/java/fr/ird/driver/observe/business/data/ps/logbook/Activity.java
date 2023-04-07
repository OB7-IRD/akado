package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.common.DateUtils;
import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.referential.common.DataQuality;
import fr.ird.driver.observe.business.referential.common.FpaZone;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.common.Wind;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.ReasonForNoFishing;
import fr.ird.driver.observe.business.referential.ps.common.ReasonForNullSet;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;
import fr.ird.driver.observe.business.referential.ps.common.VesselActivity;
import fr.ird.driver.observe.business.referential.ps.logbook.InformationSource;
import fr.ird.driver.observe.business.referential.ps.logbook.SetSuccessStatus;
import fr.ird.driver.observe.common.ObserveUtils;
import io.ultreia.java4all.util.Dates;
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
    private int setCount;
    private float seaSurfaceTemperature;
    private int windDirection;
    private float totalWeight;
    private float currentSpeed;
    private int currentDirection;
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

    /**
     * Used by anapo (this is the trip.vessel).
     */
    private Vessel vessel;
    /**
     * Used by anapo (this is the trip.endDate).
     */
    private Date landingDate;
    /**
     * Used by anapo (this is the route date).
     */
    private Date date;
    /**
     * Used by anapo (this this the full date of the activity).
     */
    private Date fullDate;

    /**
     * Is position in indian ocean? (cache of gis request).
     */
    private Supplier<Boolean> positionInIndianOcean;
    /**
     * Is position in atlantic ocean? (cache of gis request).
     */
    private Supplier<Boolean> positionInAtlanticOcean;
    /**
     * in land country (cache of gis request).
     */
    private Supplier<String> positionInLand;

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

    public int getSetCount() {
        return setCount;
    }

    public void setSetCount(int setCount) {
        this.setCount = setCount;
    }

    public float getSeaSurfaceTemperature() {
        return seaSurfaceTemperature;
    }

    public void setSeaSurfaceTemperature(float seaSurfaceTemperature) {
        this.seaSurfaceTemperature = seaSurfaceTemperature;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int currentDirection) {
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
        if (withoutCoordinate()) {
            return 0;
        }
        return ObserveUtils.getQuadrant(getLongitude(), getLatitude());
    }

    /**
     * Calculate the total catches weight with discards.
     *
     * @return the sum of catches weight.
     */
    public double totalCatchWeightFromCatches() {
        return getCatches().stream().mapToDouble(Catch::getWeight).sum();
    }

    public String getID(Trip trip, Route route) {
        String fullDate = getTime() == null ? DateUtils.formatDate(route.getDate()) + " ??:??" : DateUtils.formatDateAndTime(Dates.getDateAndTime(route.getDate(), getTime(), false, false));
        return String.format("%s %s %s", trip.getID(), fullDate, getNumber());
    }

    public String getID() {
        String fullDate = getTime() == null ? DateUtils.formatDate(getDate()) + " ??:??" : DateUtils.formatDateAndTime(Dates.getDateAndTime(getDate(), getTime(), false, false));
        String tripID = String.format("%s %s", getVessel().getID(), DateUtils.formatDate(getLandingDate()));
        return String.format("%s %s %s", tripID, fullDate, getNumber());
    }

    public boolean withoutCoordinate() {
        return getLatitude() == null || getLongitude() == null;
    }

    public boolean withoutFpaZone() {
        return getFpaZone() == null;
    }

    public boolean withoutTime() {
        return getTime() == null;
    }

    public boolean withoutSchoolType() {
        return getSchoolType() == null;
    }

    public boolean requiresCoordinate() {
        if (!withoutCoordinate()) {
            return false;
        }
        boolean activityRequiresCoordinate = !getVesselActivity().isPureFadOperation();
        if (!activityRequiresCoordinate) {
            // state: on a pure FAD operation (code 13)
            activityRequiresCoordinate = getFloatingObject().isEmpty();
            if (!activityRequiresCoordinate) {
                // state: with at least one FAD
                for (FloatingObject floatingObject : getFloatingObject()) {
                    activityRequiresCoordinate = floatingObjectRequireCoordinate(floatingObject);
                    if (activityRequiresCoordinate) {
                        // as soon as one floatingObject is on error quit
                        break;
                    }
                }
            }
        }
        return activityRequiresCoordinate;
    }

    public boolean floatingObjectRequireCoordinate(FloatingObject floatingObject) {
        if (floatingObject.getTransmittingBuoy().isEmpty()) {
            return true;
        }
        for (TransmittingBuoy transmittingBuoy : floatingObject.getTransmittingBuoy()) {
            if (transmittingBuoyRequireCoordinate(transmittingBuoy)) {
                return true;
            }
        }
        return false;
    }

    public boolean transmittingBuoyRequireCoordinate(TransmittingBuoy transmittingBuoy) {
        return !transmittingBuoy.getTransmittingBuoyOperation().isLost()
                && transmittingBuoy.getTransmittingBuoyOperation().isEndOfUse();
    }

    public boolean containsFishingBaitsObservedSystem() {
        return getObservedSystem().stream().anyMatch(ObservedSystem::isFishingBaits);
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(Date landingDate) {
        this.landingDate = landingDate;
    }

    public Date getFullDate() {
        if (fullDate == null) {
            if (getTime() == null) {
                fullDate = getDate();
            } else {
                fullDate = Dates.getDateAndTime(getDate(), getTime(), false, false);
            }
        }
        return fullDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.fullDate = null;
    }

    public Supplier<Boolean> getPositionInIndianOcean() {
        return positionInIndianOcean;
    }

    public Supplier<Boolean> getPositionInAtlanticOcean() {
        return positionInAtlanticOcean;
    }

    public Supplier<String> getPositionInLand() {
        return positionInLand;
    }

    public void setPositionInIndianOcean(Supplier<Boolean> positionInIndianOcean) {
        this.positionInIndianOcean = positionInIndianOcean;
    }

    public void setPositionInAtlanticOcean(Supplier<Boolean> positionInAtlanticOcean) {
        this.positionInAtlanticOcean = positionInAtlanticOcean;
    }

    public void setPositionInLand(Supplier<String> positionInLand) {
        this.positionInLand = positionInLand;
    }
}
