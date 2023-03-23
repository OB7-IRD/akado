/* $Id: Trip.java 506 2014-12-17 23:06:25Z lebranch $
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */package fr.ird.driver.avdth.business;

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Les marées
 *
 * @pdOid 7227ea1e-9fe3-4f00-bc6a-614b10e45496
 *
 * $LastChangedDate: 2014-12-18 00:06:25 +0100 (jeu. 18 déc. 2014) $
 *
 * $LastChangedRevision: 506 $
 */
public class Trip implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    /**
     * DateTime de débarquement
     *
     * @pdOid 4ce18154-59e4-4094-9007-183f3e298cca
     */

    private Harbour landingHarbour;
    /**
     * @pdOid 18aace81-4f6c-41bc-a8f2-75079836ed7f
     */
    private int timeAtSea = 0;
    /**
     * @pdOid 06d72a39-5c04-472f-b85e-18da14d4e8c9
     */
    private int fishingTime = 0;
    /**
     * @pdOid 501d309f-21b6-4a40-a8e9-9efb51514adf
     */
    private double totalLandingWeight = 0;
    /**
     * @pdOid 83fda7e7-9450-46b5-ae4e-9291501de3cc
     */
    private float localMarketWeight = 0;
    /**
     * Flag enquête : indique si le détail des activités est connu (livre de
     * bord )
     *
     * @pdOid 744d6dc3-c0f8-49de-bf5d-64bb0b512f8c
     */
    private int flagEnquete = 1;
    /**
     * @pdOid f567e453-6c03-414d-801c-510adecb6c96
     */
    private Harbour departureHarbour;
    /**
     * @pdOid 7b78b187-bb6b-4092-97c9-c1820d1cc0b7
     */
    private DateTime departureDate;
    /**
     * Ce flag indique si le débarquement est total ou partiel.
     *
     * @pdOid 4ba56d6f-9d9a-489b-a6a4-c79000c28c74
     */
    private int flagCaleVide = 1;
    private int wellAreEmptyAtStart = 0;
    private int loch;
    /**
     * Ce commentaire de 255 caractères au plus, recevra le cas échéant des
     * informations qualitatives concernant la marée dans son ensemble (y
     * compris les échantillons)
     *
     * @pdOid b8d93470-e82a-4df5-95f9-bd4adab540cb
     */
    private String commentaireMaree;
    private String numeroMaree;
    private String codeObServeTopiaid;

    public List<Activity> getActivites() {
        if (activites == null) {
            activites = new ArrayList<Activity>();
        }
        return activites;
    }

    public void setActivites(List<Activity> activites) {
        this.activites = activites;
    }
    private List<Activity> activites;
    private List<ElementaryLanding> lotsCommerciaux;

    public List<ElementaryLanding> getLotsCommerciaux() {
        if (lotsCommerciaux == null) {
            lotsCommerciaux = new ArrayList<ElementaryLanding>();
        }
        return lotsCommerciaux;
    }

    public void setLotsCommerciaux(List<ElementaryLanding> lotsCommerciaux) {
        this.lotsCommerciaux = lotsCommerciaux;
    }

    /**
     * @pdRoleInfo migr=no name=ZoneGeographique assc=refZoneGeoMaree mult=0..1
     * side=A
     */
    private GeographicalArea zoneGeographique;

    public Trip() {
    }

    public Trip(Vessel b, DateTime dbq) {
        this.vessel = b;
        this.landingDate = dbq;
    }

    public String getNumeroMaree() {
        return numeroMaree;
    }

    public void setNumeroMaree(String numeroMaree) {
        this.numeroMaree = numeroMaree;
    }
//

    public Harbour getLandingHarbour() {
        return landingHarbour;
    }

    public void setLandingHarbour(Harbour port) {
        this.landingHarbour = port;
    }

    public int getTimeAtSea() {
        return timeAtSea;
    }

    public void setTimeAtSea(int timeAtSea) {
        this.timeAtSea = timeAtSea;
    }

    public int getFishingTime() {
        return fishingTime;
    }

    public void setFishingTime(int fishingTime) {
        this.fishingTime = fishingTime;
    }

    public double getTotalLandingWeight() {
        return totalLandingWeight;
    }

    public void setTotalLandingWeight(double totalLandingWeight) {
        this.totalLandingWeight = totalLandingWeight;
    }

    public float getLocalMarketWeight() {
        return localMarketWeight;
    }

    public void setLocalMarketWeight(float localMarketWeight) {
        this.localMarketWeight = localMarketWeight;
    }

    public int getFlagEnquete() {
        return flagEnquete;
    }

    public void setFlagEnquete(int flagEnquete) {
        this.flagEnquete = flagEnquete;
    }

    public Harbour getDepartureHarbour() {
        return departureHarbour;
    }

    public void setDepartureHarbour(Harbour departureHarbour) {
        this.departureHarbour = departureHarbour;
    }

    public DateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(DateTime DepartureDate) {
        this.departureDate = DepartureDate;
    }

    public int getFlagCaleVide() {
        return flagCaleVide;
    }

    public void setFlagCaleVide(int flagCaleVide) {
        this.flagCaleVide = flagCaleVide;
    }

    public int getLoch() {
        return loch;
    }

    public void setLoch(int loch) {
        this.loch = loch;
    }

    public java.lang.String getCommentaireMaree() {
        return commentaireMaree;
    }

    public void setCommentaireMaree(java.lang.String commentaireMaree) {
        this.commentaireMaree = commentaireMaree;
    }

    public GeographicalArea getZoneGeographique() {
        return zoneGeographique;
    }

    public void setZoneGeographique(GeographicalArea zoneGeographique) {
        this.zoneGeographique = zoneGeographique;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public DateTime getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(DateTime landingDate) {
        this.landingDate = landingDate;
    }

    public void setCodeObServeTopiaid(String codeObServeTopiaid) {
        this.codeObServeTopiaid = codeObServeTopiaid;
    }

    public String getCodeObServeTopiaid() {
        return codeObServeTopiaid;
    }

    @Override
    public String toString() {
        return "Maree{" + "bateau=" + vessel.getID() + ", dateArrivee=" + landingDate + ", portDebarquement=" + landingHarbour + "}";//+ ", timeAtSea=" + timeAtSea + ", fishingTime=" + fishingTime + ", totalLandingWeight=" + totalLandingWeight + ", localMarketWeight=" + localMarketWeight + ", flagEnquete=" + flagEnquete + ", departureHarbour=" + departureHarbour + ", departureDate=" + departureDate + ", flagCaleVide=" + flagCaleVide + ", loch=" + loch + ", commentaireMaree=" + commentaireMaree + ", numeroMaree=" + numeroMaree + ", codeObServeTopiaid=" + codeObServeTopiaid + ", zoneGeographique=" + zoneGeographique + '}';
    }



    public void setWellAreEmptyAtStart(int wellAreEmptyAtStart) {
        this.wellAreEmptyAtStart = wellAreEmptyAtStart;
    }

    public int getWellAreEmptyAtStart() {
        return wellAreEmptyAtStart;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
       hash = 89 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 89 * hash + (this.landingHarbour != null ? this.landingHarbour.hashCode() : 0);
        hash = 89 * hash + this.timeAtSea;
        hash = 89 * hash + this.fishingTime;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.totalLandingWeight) ^ (Double.doubleToLongBits(this.totalLandingWeight) >>> 32));
        hash = 89 * hash + Float.floatToIntBits(this.localMarketWeight);
        hash = 89 * hash + this.flagEnquete;
        hash = 89 * hash + (this.departureHarbour != null ? this.departureHarbour.hashCode() : 0);
        hash = 89 * hash + (this.departureDate != null ? this.departureDate.hashCode() : 0);
        hash = 89 * hash + this.flagCaleVide;
        hash = 89 * hash + this.wellAreEmptyAtStart;
        hash = 89 * hash + this.loch;
        hash = 89 * hash + (this.numeroMaree != null ? this.numeroMaree.hashCode() : 0);
        hash = 89 * hash + (this.codeObServeTopiaid != null ? this.codeObServeTopiaid.hashCode() : 0);
        hash = 89 * hash + (this.activites != null ? this.activites.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Trip other = (Trip) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
     
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.landingHarbour != other.landingHarbour && (this.landingHarbour == null || !this.landingHarbour.equals(other.landingHarbour))) {
            return false;
        }
        if (this.timeAtSea != other.timeAtSea) {
            return false;
        }
        if (this.fishingTime != other.fishingTime) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalLandingWeight) != Double.doubleToLongBits(other.totalLandingWeight)) {
            return false;
        }
        if (Float.floatToIntBits(this.localMarketWeight) != Float.floatToIntBits(other.localMarketWeight)) {
            return false;
        }
        if (this.flagEnquete != other.flagEnquete) {
            return false;
        }
        if (this.departureHarbour != other.departureHarbour && (this.departureHarbour == null || !this.departureHarbour.equals(other.departureHarbour))) {
            return false;
        }
        if (this.departureDate != other.departureDate && (this.departureDate == null || !this.departureDate.equals(other.departureDate))) {
            return false;
        }
        if (this.flagCaleVide != other.flagCaleVide) {
            return false;
        }
        if (this.wellAreEmptyAtStart != other.wellAreEmptyAtStart) {
            return false;
        }
        if (this.loch != other.loch) {
            return false;
        }
        if ((this.numeroMaree == null) ? (other.numeroMaree != null) : !this.numeroMaree.equals(other.numeroMaree)) {
            return false;
        }
        if ((this.codeObServeTopiaid == null) ? (other.codeObServeTopiaid != null) : !this.codeObServeTopiaid.equals(other.codeObServeTopiaid)) {
            return false;
        }
        if (//this.activites != other.activites &&
                (this.activites == null || !this.activites.equals(other.activites))) {
            return false;
        }
        return true;
    }

    public boolean isPartialLanding() {
        return flagCaleVide == 0;
    }

    public boolean hasLogbook() {
        return flagEnquete == 1;
    }

    @Override
    public String getID() {
        return String.format("%s %s", getVessel().getID(), DATE_FORMATTER.print(getLandingDate()));
    }

    public Activity firstActivity() {
        if (!activites.isEmpty()) {
            return activites.get(0);
        }
        return null;
    }

    public Activity lastActivity() {
        if (!activites.isEmpty()) {
            return activites.get(activites.size() - 1);
        }
        return null;
    }

}
