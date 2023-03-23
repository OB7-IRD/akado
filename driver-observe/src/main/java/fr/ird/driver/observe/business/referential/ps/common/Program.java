package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
import fr.ird.driver.observe.business.referential.common.Organism;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Program extends I18nReferentialEntity {

    private Date startDate;
    private Date endDate;
    private String comment;
    private boolean observation;
    private boolean logbook;
    private Supplier<Organism> organism = () -> null;

    public Organism getOrganism() {
        return organism.get();
    }

    public void setOrganism(Supplier<Organism> organism) {
        this.organism = Objects.requireNonNull(organism);
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

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
}
