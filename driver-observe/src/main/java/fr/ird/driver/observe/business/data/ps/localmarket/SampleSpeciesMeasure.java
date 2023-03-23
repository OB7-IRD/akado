package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleSpeciesMeasure extends DataEntity {

    private Integer count;
    private Float sizeClass;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getSizeClass() {
        return sizeClass;
    }

    public void setSizeClass(Float sizeClass) {
        this.sizeClass = sizeClass;
    }
}
