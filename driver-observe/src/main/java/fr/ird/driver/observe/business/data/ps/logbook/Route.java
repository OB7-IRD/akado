package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Route extends DataEntity {

    private String comment;
    private Date date;
    private Integer timeAtSea;
    private Integer fishingTime;
    private Supplier<Set<Activity>> activity = SingletonSupplier.of(LinkedHashSet::new);

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Set<Activity> getActivity() {
        return activity.get();
    }

    public void setActivity(Supplier<Set<Activity>> activity) {
        this.activity = Objects.requireNonNull(activity);
    }
}
