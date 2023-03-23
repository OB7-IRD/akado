package fr.ird.driver.observe.business.referential.ps.localmarket;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
import fr.ird.driver.observe.business.referential.common.Harbour;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Packaging extends I18nReferentialEntity {

    private Date startDate;
    private Date endDate;
    private Float meanWeight;
    private Supplier<BatchComposition> batchComposition = () -> null;
    private Supplier<BatchWeightType> batchWeightType = () -> null;
    private Supplier<Harbour> harbour = () -> null;

    public BatchComposition getBatchComposition() {
        return batchComposition.get();
    }

    public void setBatchComposition(Supplier<BatchComposition> batchComposition) {
        this.batchComposition = Objects.requireNonNull(batchComposition);
    }

    public BatchWeightType getBatchWeightType() {
        return batchWeightType.get();
    }

    public void setBatchWeightType(Supplier<BatchWeightType> batchWeightType) {
        this.batchWeightType = Objects.requireNonNull(batchWeightType);
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

    public Float getMeanWeight() {
        return meanWeight;
    }

    public void setMeanWeight(Float meanWeight) {
        this.meanWeight = meanWeight;
    }

    public Harbour getHarbour() {
        return harbour.get();
    }

    public void setHarbour(Supplier<Harbour> harbour) {
        this.harbour = Objects.requireNonNull(harbour);
    }
}
