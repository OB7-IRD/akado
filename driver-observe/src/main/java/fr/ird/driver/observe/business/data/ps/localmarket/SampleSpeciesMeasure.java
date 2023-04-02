package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleSpeciesMeasure extends DataEntity {

    private int count;
    private float sizeClass;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getSizeClass() {
        return sizeClass;
    }

    public void setSizeClass(float sizeClass) {
        this.sizeClass = sizeClass;
    }
}
