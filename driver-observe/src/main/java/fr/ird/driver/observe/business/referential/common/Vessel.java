package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Vessel extends I18nReferentialEntity {

    private Integer keelCode;
    private Date changeDate;
    private Integer yearService;
    private double length;
    private double capacity;
    private Integer powerCv;
    private Integer powerKW;
    private double searchMaximum;
    private String comment;
    private String source;
    private String iccatId;
    private String iotcId;
    private String nationalId;
    private String comId;
    private String tuviId;
    private String imoId;
    private String radioCallSignId;
    private String lloydId;
    private String cfrId;
    private String wellRegex;
    private Date startDate;
    private Date endDate;
    private Supplier<VesselSizeCategory> vesselSizeCategory = () -> null;
    private Supplier<VesselType> vesselType = () -> null;
    private Supplier<Country> flagCountry = () -> null;
    private Supplier<Country> fleetCountry = () -> null;
    private Supplier<ShipOwner> shipOwner = () -> null;

    public static final Set<String> SEINE_VESSEL_TYPE = Set.of(
            "fr.ird.referential.common.VesselType#1239832675735#0.7380146830307519",
            "fr.ird.referential.common.VesselType#1239832675735#0.9086075071905084",
            "fr.ird.referential.common.VesselType#1239832675735#0.307197212385357");

    public static final Set<String> BAIT_VESSEL_TYPE = Set.of(
            "fr.ird.referential.common.VesselType#1239832675734#0.24685054061673772",
            "fr.ird.referential.common.VesselType#1308149674400#0.8030832839591066",
            "fr.ird.referential.common.VesselType#1239832675734#0.4191950326431938");

    public boolean isPurseSeine() {
        return SEINE_VESSEL_TYPE.contains(getVesselType().getTopiaId());
    }

    public boolean isBaitBoat() {
        return BAIT_VESSEL_TYPE.contains(getVesselType().getTopiaId());
    }

    public int getCodeAsInt() {
        return Integer.parseInt(getCode());
    }

    public Integer getKeelCode() {
        return keelCode;
    }

    public void setKeelCode(Integer keelCode) {
        this.keelCode = keelCode;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public Integer getYearService() {
        return yearService;
    }

    public void setYearService(Integer yearService) {
        this.yearService = yearService;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public Integer getPowerCv() {
        return powerCv;
    }

    public void setPowerCv(Integer powerCv) {
        this.powerCv = powerCv;
    }

    public Integer getPowerKW() {
        return powerKW;
    }

    public void setPowerKW(Integer powerKW) {
        this.powerKW = powerKW;
    }

    public double getSearchMaximum() {
        return searchMaximum;
    }

    public void setSearchMaximum(double searchMaximum) {
        this.searchMaximum = searchMaximum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIccatId() {
        return iccatId;
    }

    public void setIccatId(String iccatId) {
        this.iccatId = iccatId;
    }

    public String getIotcId() {
        return iotcId;
    }

    public void setIotcId(String iotcId) {
        this.iotcId = iotcId;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getTuviId() {
        return tuviId;
    }

    public void setTuviId(String tuviId) {
        this.tuviId = tuviId;
    }

    public String getImoId() {
        return imoId;
    }

    public void setImoId(String imoId) {
        this.imoId = imoId;
    }

    public String getRadioCallSignId() {
        return radioCallSignId;
    }

    public void setRadioCallSignId(String radioCallSignId) {
        this.radioCallSignId = radioCallSignId;
    }

    public String getLloydId() {
        return lloydId;
    }

    public void setLloydId(String lloydId) {
        this.lloydId = lloydId;
    }

    public String getCfrId() {
        return cfrId;
    }

    public void setCfrId(String cfrId) {
        this.cfrId = cfrId;
    }

    public String getWellRegex() {
        return wellRegex;
    }

    public void setWellRegex(String wellRegex) {
        this.wellRegex = wellRegex;
    }

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

    public VesselSizeCategory getVesselSizeCategory() {
        return vesselSizeCategory.get();
    }

    public void setVesselSizeCategory(Supplier<VesselSizeCategory> vesselSizeCategory) {
        this.vesselSizeCategory = Objects.requireNonNull(vesselSizeCategory);
    }

    public VesselType getVesselType() {
        return vesselType.get();
    }

    public void setVesselType(Supplier<VesselType> vesselType) {
        this.vesselType = Objects.requireNonNull(vesselType);
    }

    public Country getFlagCountry() {
        return flagCountry.get();
    }

    public void setFlagCountry(Supplier<Country> flagCountry) {
        this.flagCountry = Objects.requireNonNull(flagCountry);
    }

    public Country getFleetCountry() {
        return fleetCountry.get();
    }

    public void setFleetCountry(Supplier<Country> fleetCountry) {
        this.fleetCountry = Objects.requireNonNull(fleetCountry);
    }

    public ShipOwner getShipOwner() {
        return shipOwner.get();
    }

    public void setShipOwner(Supplier<ShipOwner> shipOwner) {
        this.shipOwner = Objects.requireNonNull(shipOwner);
    }

    public String getID() {
        return "" + getCode();
    }
}
