package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Wind extends I18nReferentialEntity {
    private Integer minSpeed;
    private Integer maxSpeed;
    private Float minSwellHeight;
    private Float maxSwellHeight;

    public Integer getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(Integer minSpeed) {
        this.minSpeed = minSpeed;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Float getMinSwellHeight() {
        return minSwellHeight;
    }

    public void setMinSwellHeight(Float minSwellHeight) {
        this.minSwellHeight = minSwellHeight;
    }

    public Float getMaxSwellHeight() {
        return maxSwellHeight;
    }

    public void setMaxSwellHeight(Float maxSwellHeight) {
        this.maxSwellHeight = maxSwellHeight;
    }
}
