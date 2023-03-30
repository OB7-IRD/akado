package fr.ird.driver.observe.business.data.ps.common;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.data.ps.landing.Landing;
import fr.ird.driver.observe.business.data.ps.localmarket.Batch;
import fr.ird.driver.observe.business.data.ps.localmarket.Sample;
import fr.ird.driver.observe.business.data.ps.localmarket.Survey;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.data.ps.logbook.Well;
import fr.ird.driver.observe.business.referential.common.DataQuality;
import fr.ird.driver.observe.business.referential.common.Harbour;
import fr.ird.driver.observe.business.referential.common.Ocean;
import fr.ird.driver.observe.business.referential.common.Person;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.ps.common.AcquisitionStatus;
import fr.ird.driver.observe.business.referential.ps.common.Program;
import fr.ird.driver.observe.business.referential.ps.logbook.WellContentStatus;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.ArrayList;
import java.util.Collections;
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
public class Trip extends DataEntity {

    private Date startDate;
    private Date endDate;
    private String formsUrl;
    private String reportsUrl;
    private boolean historicalData;
    private String ersId;
    private Integer timeAtSea;
    private Integer fishingTime;
    private Float landingTotalWeight;
    private Float localMarketTotalWeight;
    private Integer loch;
    private String generalComment;
    private String logbookComment;
    private Supplier<Ocean> ocean = () -> null;
    private Supplier<Vessel> vessel = () -> null;
    private Supplier<Program> logbookProgram = () -> null;
    private Supplier<Person> observer = () -> null;
    private Supplier<Person> captain = () -> null;
    private Supplier<Person> logbookDataEntryOperator = () -> null;
    private Supplier<Harbour> departureHarbour = () -> null;
    private Supplier<Harbour> landingHarbour = () -> null;
    private Supplier<DataQuality> logbookDataQuality = () -> null;
    private Supplier<WellContentStatus> departureWellContentStatus = () -> null;
    private Supplier<WellContentStatus> landingWellContentStatus = () -> null;
    private Supplier<AcquisitionStatus> logbookAcquisitionStatus = () -> null;
    private Supplier<AcquisitionStatus> targetWellsSamplingAcquisitionStatus = () -> null;
    private Supplier<AcquisitionStatus> landingAcquisitionStatus = () -> null;
    private Supplier<AcquisitionStatus> localMarketAcquisitionStatus = () -> null;
    private Supplier<AcquisitionStatus> localMarketWellsSamplingAcquisitionStatus = () -> null;
    private Supplier<AcquisitionStatus> localMarketSurveySamplingAcquisitionStatus = () -> null;
    private Supplier<Set<Route>> logbookRoute = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<Well>> well = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<fr.ird.driver.observe.business.data.ps.logbook.Sample>> logbookSample = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<List<Landing>> landing = SingletonSupplier.of(LinkedList::new);
    private Supplier<Set<Batch>> localmarketBatch = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<Survey>> localmarketSurvey = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<Sample>> localmarketSample = SingletonSupplier.of(LinkedHashSet::new);

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFormsUrl() {
        return formsUrl;
    }

    public void setFormsUrl(String formsUrl) {
        this.formsUrl = formsUrl;
    }

    public String getReportsUrl() {
        return reportsUrl;
    }

    public void setReportsUrl(String reportsUrl) {
        this.reportsUrl = reportsUrl;
    }

    public boolean isHistoricalData() {
        return historicalData;
    }

    public void setHistoricalData(boolean historicalData) {
        this.historicalData = historicalData;
    }

    public String getErsId() {
        return ersId;
    }

    public void setErsId(String ersId) {
        this.ersId = ersId;
    }

    public Ocean getOcean() {
        return ocean.get();
    }

    public void setOcean(Supplier<Ocean> ocean) {
        this.ocean = Objects.requireNonNull(ocean);
    }

    public Integer getTimeAtSea() {
        return timeAtSea;
    }

    public void setTimeAtSea(Integer timeAtSea) {
        this.timeAtSea = timeAtSea;
    }

    public Integer getFishingTime() {
        return fishingTime;
    }

    public void setFishingTime(Integer fishingTime) {
        this.fishingTime = fishingTime;
    }

    public Float getLandingTotalWeight() {
        return landingTotalWeight;
    }

    public void setLandingTotalWeight(Float landingTotalWeight) {
        this.landingTotalWeight = landingTotalWeight;
    }

    public Float getLocalMarketTotalWeight() {
        return localMarketTotalWeight;
    }

    public void setLocalMarketTotalWeight(Float localMarketTotalWeight) {
        this.localMarketTotalWeight = localMarketTotalWeight;
    }

    public Integer getLoch() {
        return loch;
    }

    public void setLoch(Integer loch) {
        this.loch = loch;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    public String getLogbookComment() {
        return logbookComment;
    }

    public void setLogbookComment(String logbookComment) {
        this.logbookComment = logbookComment;
    }

    public Vessel getVessel() {
        return vessel.get();
    }

    public void setVessel(Supplier<Vessel> vessel) {
        this.vessel = Objects.requireNonNull(vessel);
    }

    public Program getLogbookProgram() {
        return logbookProgram.get();
    }

    public void setLogbookProgram(Supplier<Program> logbookProgram) {
        this.logbookProgram = Objects.requireNonNull(logbookProgram);
    }

    public Person getObserver() {
        return observer.get();
    }

    public void setObserver(Supplier<Person> observer) {
        this.observer = Objects.requireNonNull(observer);
    }

    public Person getCaptain() {
        return captain.get();
    }

    public void setCaptain(Supplier<Person> captain) {
        this.captain = Objects.requireNonNull(captain);
    }

    public Person getLogbookDataEntryOperator() {
        return logbookDataEntryOperator.get();
    }

    public void setLogbookDataEntryOperator(Supplier<Person> logbookDataEntryOperator) {
        this.logbookDataEntryOperator = Objects.requireNonNull(logbookDataEntryOperator);
    }

    public Harbour getDepartureHarbour() {
        return departureHarbour.get();
    }

    public void setDepartureHarbour(Supplier<Harbour> departureHarbour) {
        this.departureHarbour = Objects.requireNonNull(departureHarbour);
    }

    public Harbour getLandingHarbour() {
        return landingHarbour.get();
    }

    public void setLandingHarbour(Supplier<Harbour> landingHarbour) {
        this.landingHarbour = Objects.requireNonNull(landingHarbour);
    }

    public DataQuality getLogbookDataQuality() {
        return logbookDataQuality.get();
    }

    public void setLogbookDataQuality(Supplier<DataQuality> logbookDataQuality) {
        this.logbookDataQuality = Objects.requireNonNull(logbookDataQuality);
    }

    public WellContentStatus getDepartureWellContentStatus() {
        return departureWellContentStatus.get();
    }

    public void setDepartureWellContentStatus(Supplier<WellContentStatus> departureWellContentStatus) {
        this.departureWellContentStatus = Objects.requireNonNull(departureWellContentStatus);
    }

    public WellContentStatus getLandingWellContentStatus() {
        return landingWellContentStatus.get();
    }

    public void setLandingWellContentStatus(Supplier<WellContentStatus> landingWellContentStatus) {
        this.landingWellContentStatus = Objects.requireNonNull(landingWellContentStatus);
    }

    public AcquisitionStatus getLogbookAcquisitionStatus() {
        return logbookAcquisitionStatus.get();
    }

    public void setLogbookAcquisitionStatus(Supplier<AcquisitionStatus> logbookAcquisitionStatus) {
        this.logbookAcquisitionStatus = Objects.requireNonNull(logbookAcquisitionStatus);
    }

    public AcquisitionStatus getTargetWellsSamplingAcquisitionStatus() {
        return targetWellsSamplingAcquisitionStatus.get();
    }

    public void setTargetWellsSamplingAcquisitionStatus(Supplier<AcquisitionStatus> targetWellsSamplingAcquisitionStatus) {
        this.targetWellsSamplingAcquisitionStatus = Objects.requireNonNull(targetWellsSamplingAcquisitionStatus);
    }

    public AcquisitionStatus getLandingAcquisitionStatus() {
        return landingAcquisitionStatus.get();
    }

    public void setLandingAcquisitionStatus(Supplier<AcquisitionStatus> landingAcquisitionStatus) {
        this.landingAcquisitionStatus = Objects.requireNonNull(landingAcquisitionStatus);
    }

    public AcquisitionStatus getLocalMarketAcquisitionStatus() {
        return localMarketAcquisitionStatus.get();
    }

    public void setLocalMarketAcquisitionStatus(Supplier<AcquisitionStatus> localMarketAcquisitionStatus) {
        this.localMarketAcquisitionStatus = Objects.requireNonNull(localMarketAcquisitionStatus);
    }

    public AcquisitionStatus getLocalMarketWellsSamplingAcquisitionStatus() {
        return localMarketWellsSamplingAcquisitionStatus.get();
    }

    public void setLocalMarketWellsSamplingAcquisitionStatus(Supplier<AcquisitionStatus> localMarketWellsSamplingAcquisitionStatus) {
        this.localMarketWellsSamplingAcquisitionStatus = Objects.requireNonNull(localMarketWellsSamplingAcquisitionStatus);
    }

    public AcquisitionStatus getLocalMarketSurveySamplingAcquisitionStatus() {
        return localMarketSurveySamplingAcquisitionStatus.get();
    }

    public void setLocalMarketSurveySamplingAcquisitionStatus(Supplier<AcquisitionStatus> localMarketSurveySamplingAcquisitionStatus) {
        this.localMarketSurveySamplingAcquisitionStatus = Objects.requireNonNull(localMarketSurveySamplingAcquisitionStatus);
    }

    public Set<Route> getLogbookRoute() {
        return logbookRoute.get();
    }

    public void setLogbookRoute(Supplier<Set<Route>> logbookRoute) {
        this.logbookRoute = Objects.requireNonNull(logbookRoute);
    }

    public Set<Well> getWell() {
        return well.get();
    }

    public void setWell(Supplier<Set<Well>> well) {
        this.well = Objects.requireNonNull(well);
    }

    public Set<fr.ird.driver.observe.business.data.ps.logbook.Sample> getLogbookSample() {
        return logbookSample.get();
    }

    public void setLogbookSample(Supplier<Set<fr.ird.driver.observe.business.data.ps.logbook.Sample>> logbookSample) {
        this.logbookSample = Objects.requireNonNull(logbookSample);
    }

    public List<Landing> getLanding() {
        return landing.get();
    }

    public void setLanding(Supplier<List<Landing>> landing) {
        this.landing = Objects.requireNonNull(landing);
    }

    public Set<Batch> getLocalmarketBatch() {
        return localmarketBatch.get();
    }

    public void setLocalmarketBatch(Supplier<Set<Batch>> localmarketBatch) {
        this.localmarketBatch = Objects.requireNonNull(localmarketBatch);
    }

    public Set<Survey> getLocalmarketSurvey() {
        return localmarketSurvey.get();
    }

    public void setLocalmarketSurvey(Supplier<Set<Survey>> localmarketSurvey) {
        this.localmarketSurvey = Objects.requireNonNull(localmarketSurvey);
    }

    public Set<Sample> getLocalmarketSample() {
        return localmarketSample.get();
    }

    public void setLocalmarketSample(Supplier<Set<Sample>> localmarketSample) {
        this.localmarketSample = Objects.requireNonNull(localmarketSample);
    }

    public boolean hasLogbook() {
        return getLogbookAcquisitionStatus() != null && getLogbookAcquisitionStatus().isFieldEnabler();
    }

    public boolean withLogbookActivities() {
        for (Route route : getLogbookRoute()) {
            if (route.getActivity().size() > 1) {
                return true;
            }
        }
        return false;
    }

    public Route firstRouteWithActivity() {
        for (Route route : getLogbookRoute()) {
            if (route.getActivity().size() > 1) {
                return route;
            }
        }
        return null;
    }

    public Route lastRouteWithActivity() {
        List<Route> routes = new ArrayList<>(getLogbookRoute());
        Collections.reverse(routes);
        for (Route route : routes) {
            if (route.getActivity().size() > 1) {
                return route;
            }
        }
        return null;
    }

    public boolean isPartialLanding() {
        //FIXME getLandingWellContentStatus() ne devrait pas etre null, ajouter un nouveau controle sur le sujet
        return getLandingWellContentStatus() != null && getLandingWellContentStatus().getTopiaId().equals("fr.ird.referential.ps.logbook.WellContentStatus#1464000000000#02");
    }
}
