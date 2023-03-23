package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class AcquisitionStatus extends I18nReferentialEntity {

    private boolean observation;
    private boolean logbook;
    private boolean landing;
    private boolean targetWellsSampling;
    private boolean localMarket;
    private boolean localMarketWellsSampling;
    private boolean localMarketSurveySampling;
    private boolean advancedSampling;
    private boolean fieldEnabler;

    public boolean isObservation() {
        return observation;
    }

    public void setObservation(boolean observation) {
        this.observation = observation;
    }

    public boolean isLogbook() {
        return logbook;
    }

    public void setLogbook(boolean logbook) {
        this.logbook = logbook;
    }

    public boolean isLanding() {
        return landing;
    }

    public void setLanding(boolean landing) {
        this.landing = landing;
    }

    public boolean isTargetWellsSampling() {
        return targetWellsSampling;
    }

    public void setTargetWellsSampling(boolean targetWellsSampling) {
        this.targetWellsSampling = targetWellsSampling;
    }

    public boolean isLocalMarket() {
        return localMarket;
    }

    public void setLocalMarket(boolean localMarket) {
        this.localMarket = localMarket;
    }

    public boolean isLocalMarketWellsSampling() {
        return localMarketWellsSampling;
    }

    public void setLocalMarketWellsSampling(boolean localMarketWellsSampling) {
        this.localMarketWellsSampling = localMarketWellsSampling;
    }

    public boolean isLocalMarketSurveySampling() {
        return localMarketSurveySampling;
    }

    public void setLocalMarketSurveySampling(boolean localMarketSurveySampling) {
        this.localMarketSurveySampling = localMarketSurveySampling;
    }

    public boolean isAdvancedSampling() {
        return advancedSampling;
    }

    public void setAdvancedSampling(boolean advancedSampling) {
        this.advancedSampling = advancedSampling;
    }

    public boolean isFieldEnabler() {
        return fieldEnabler;
    }

    public void setFieldEnabler(boolean fieldEnabler) {
        this.fieldEnabler = fieldEnabler;
    }
}
