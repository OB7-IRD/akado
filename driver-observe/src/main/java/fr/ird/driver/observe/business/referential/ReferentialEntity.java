package fr.ird.driver.observe.business.referential;

import fr.ird.driver.observe.business.Entity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ReferentialEntity extends Entity {

    /**
     * Nom de l'attribut en BD : code
     */
    protected java.lang.String code;
    /**
     * Nom de l'attribut en BD : uri
     */
    protected java.lang.String uri;
    /**
     * Nom de l'attribut en BD : homeId
     */
    protected java.lang.String homeId;
    /**
     * Nom de l'attribut en BD : needComment
     */
    protected boolean needComment;
    /**
     * Nom de l'attribut en BD : status
     */
    protected int status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getHomeId() {
        return homeId;
    }

    @Override
    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public boolean isNeedComment() {
        return needComment;
    }

    public void setNeedComment(boolean needComment) {
        this.needComment = needComment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
