package fr.ird.driver.observe.business;

import java.util.Date;
import java.util.Objects;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Entity {

    protected String topiaId;
    protected long topiaVersion;
    protected Date topiaCreateDate;
    protected Date lastUpdateDate;
    protected String homeId;

    public String getTopiaId() {
        return topiaId;
    }

    public void setTopiaId(String topiaId) {
        this.topiaId = topiaId;
    }

    public long getTopiaVersion() {
        return topiaVersion;
    }

    public void setTopiaVersion(long topiaVersion) {
        this.topiaVersion = topiaVersion;
    }

    public Date getTopiaCreateDate() {
        return topiaCreateDate;
    }

    public void setTopiaCreateDate(Date topiaCreateDate) {
        this.topiaCreateDate = topiaCreateDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return Objects.equals(topiaId, entity.topiaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topiaId);
    }
}
