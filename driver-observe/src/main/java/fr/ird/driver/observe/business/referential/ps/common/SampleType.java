package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Date;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleType extends I18nReferentialEntity {

    private boolean localMarket;
    private boolean logbook;
    private Date startDate;
    private Date endDate;

    public boolean isLocalMarket() {
        return localMarket;
    }

    public void setLocalMarket(boolean localMarket) {
        this.localMarket = localMarket;
    }

    public boolean isLogbook() {
        return logbook;
    }

    public void setLogbook(boolean logbook) {
        this.logbook = logbook;
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
}
