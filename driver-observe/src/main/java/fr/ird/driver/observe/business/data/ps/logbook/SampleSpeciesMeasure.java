package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleSpeciesMeasure extends DataEntity {

    private double sizeClass;
    private int count;

    public double getSizeClass() {
        return sizeClass;
    }

    public void setSizeClass(double sizeClass) {
        this.sizeClass = sizeClass;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
