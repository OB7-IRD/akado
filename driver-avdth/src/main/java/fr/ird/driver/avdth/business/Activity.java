/**
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.business;

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.joda.time.DateTime;

/**
 * Defines the Class <em>Activity<em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 3.3
 * @since 3.3
 * @date 01 juin 2013
 *
 */
public class Activity implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    private SchoolType schoolType;
    private LogType logType;
    private BuoyType buoyType;
    /**
     * Date (jj/mm/aaaa) de l'activité.
     */
    private DateTime date;
    private DateTime fullDate;
    /**
     * Numéro d'ordre de l'activité pour ce jour (>= 1).
     *
     */
    private int number;
    /**
     * Heure de l'activité.
     */
    private int hour;
    /**
     * Minute de l'activité
     */
    private int minute;
    /**
     * Code numérique d'océan
     */
    private int codeOcean;
    /**
     * Quadrant géographique
     */
    private int quadrant;
    /**
     * Latitude degré minute (pas de signe)
     *
     */
    private int latitude;
    /**
     * Longitude degré minute (pas de signe)
     *
     */
    private int longitude;
    /**
     * Temps de mer en heure
     *
     */
    private int timeAtSea;
    /**
     * Temps de pêche en heure
     *
     */
    private int fishingTime;
    /**
     * Nombre d'opérations élémentaires de même nature (Ex. 2 coups positifs, 3
     * coups nuls)
     *
     */
    private int operationCount;
    /**
     * Ce flag indique que l'activité a été saisie par un opérateur averti. Elle
     * n'est pas explicitement présente sur le livre de bord, mais elle
     * contribue à améliorer la description des activités élémentaires de la
     * journée de pêche.
     *
     */
    private int originalDataFlag = 1;
    /**
     * Ce flag indique que la position de l'activité a été corrigée par un
     * opérateur averti. En d'autres termes, la nouvelle valeur n'est plus
     * conforme au livre de bord, mais indubitablement plus plausible. Cette
     * correction nécessite des droits spécifiques.
     *
     */
    private int flagCorrectedPosition = 0;
    /**
     * Ce flag indique que la position de l'activité a été confrontée à celles
     * du système VMS. Ce flag est évalué et positionné par un module interne à
     * AVDTH. Ce traitement est sollicité par un opérateur disposant des droits
     * nécessaires.
     * <p>
     * F_POS_VMS_D=1 indique que les positions sont trop distantes ... (seuil à
     * préciser)</p>
     *
     */
    private int flagDivergentPositionVMS = 9;
    /**
     * Ce flag indique que l'analyse du plan de cuve confirme la qualité de
     * l'activité courante. Ce flag est déterminé par une procédure déclanchée
     * par un opérateur ayant un niveau de droit suffisant.
     *
     */
    private int flagCompatibilityWell = 9;
    /**
     * Ce flag indique que pour cette activité, des données observateurs
     * existent dans une autre base ou fichier
     *
     */
    private int flagObserver = 0;
    /**
     * Ce flag indique que l'activité présente (ou non) une qualité suffisante
     * pour être prise en considération dans les traitements en aval (T3
     * notamment). Il est essentiellement défini manuellement, il résume les
     * autres flags. Seul ce flag sera exporté dans le fichier ASCII
     *
     */
    private int flagExpert = 9;
    /**
     * Poids en tonnes
     *
     */
    private double catchWeight = 0d;
    /**
     */
    private double surfaceTemperature = 0d;
    /**
     */
    private int currentDirection;
    /**
     * Vitesse du courant en noeud (1 décimale possible)
     *
     */
    private double currentVelocity;
    /**
     */
    private int windDirection;
    /**
     * Vitesse du vent en noeud
     *
     */
    private double windVelocity;
    /**
     */
    private int flagEcoFAD;
    /**
     */
    private int buoyBelongsVessel;
    /**
     */
    private List<ElementaryCatch> elementaryCatchs;
    /**
     */
    private String buoyId;
    /**
     */
    private List<FishingContext> fishingContexts;
    /**
     */
    private Operation operation;
    /**
     *
     */

    private FPAZone fPAZone;

    private double estimatedWeight;
    private String comments;

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public Activity() {
    }

    /**
     * Constructor which copies the vessel and the landing date of the trip.
     *
     * @param trip the trip that owns the activity
     * @param date the activity date
     * @param number the activity number
     */
    public Activity(Trip trip, DateTime date, int number) {
        this(trip.getVessel(), trip.getLandingDate(), date, number);
    }

    public Activity(Vessel vessel, DateTime landingDate, DateTime date, int number) {
        this.vessel = vessel;
        this.landingDate = landingDate;
        this.date = date;
        this.number = number;
    }

    public double getCatchWeight() {
        return catchWeight;
    }

    public void setCatchWeight(Double catchWeight) {
        this.catchWeight = catchWeight;
    }

    public List<FishingContext> getFishingContexts() {
        if (fishingContexts == null) {
            fishingContexts = new ArrayList<FishingContext>();
        }
        return fishingContexts;
    }

    public void setFishingContexts(List<FishingContext> fishingContexts) {
        this.fishingContexts = fishingContexts;
    }

    public void setSurfaceTemperature(double surfaceTemperature) {
        this.surfaceTemperature = surfaceTemperature;
    }

    public void addFishingContext(FishingContext activiteAssociation) {
        if (activiteAssociation == null) {
            return;
        }
        if (this.fishingContexts == null) {
            this.fishingContexts = new ArrayList<FishingContext>();
        }
        if (!this.fishingContexts.contains(activiteAssociation)) {
            this.fishingContexts.add(activiteAssociation);
        }
    }

    public void addElementaryCatch(ElementaryCatch captureElementaire) {
        if (captureElementaire == null) {
            return;
        }
        if (this.elementaryCatchs == null) {
            this.elementaryCatchs = new ArrayList<ElementaryCatch>();
        }
        if (!this.elementaryCatchs.contains(captureElementaire)) {
            this.elementaryCatchs.add(captureElementaire);
        }
    }

    /**
     *
     * @return
     */
    public List<ElementaryCatch> getElementaryCatchs() {
        if (elementaryCatchs == null) {
            elementaryCatchs = new ArrayList<ElementaryCatch>();
        }
        return elementaryCatchs;
    }

    /**
     * @param elementaryCatchs
     */
    public void setElementaryCatchs(List<ElementaryCatch> elementaryCatchs) {
        this.elementaryCatchs = elementaryCatchs;
    }

    /**
     * @param activiteAssociation
     */
    public void removeActiviteAssociation(FishingContext activiteAssociation) {
        if (activiteAssociation == null) {
            return;
        }
        if (this.fishingContexts != null) {
            if (this.fishingContexts.contains(activiteAssociation)) {
                this.fishingContexts.remove(activiteAssociation);
            }
        }
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(SchoolType newTypeBanc) {
        if (this.schoolType == null || !this.schoolType.equals(newTypeBanc)) {
            if (newTypeBanc != null) {
                this.schoolType = newTypeBanc;
            }
        }
    }

    public FPAZone getFpaZone() {
        return fPAZone;
    }

    public void setFpaZone(FPAZone fpaZone) {
        this.fPAZone = fpaZone;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType newTypeObjet) {
        if (this.logType == null || !this.logType.equals(newTypeObjet)) {
            if (newTypeObjet != null) {
                this.logType = newTypeObjet;
            }
        }
    }

    public BuoyType getBuoyType() {
        return buoyType;
    }

    public void setBuoyType(BuoyType buoyType) {
        if (this.buoyType == null || !this.buoyType.equals(buoyType)) {
            if (buoyType != null) {
                this.buoyType = buoyType;
            }
        }
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {

        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) throws AvdthDriverException {
        this.minute = minute;
    }

    public int getCodeOcean() {
        return codeOcean;
    }

    public void setCodeOcean(int codeOcean) {
        this.codeOcean = codeOcean;
    }

    public int getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
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

    public int getOperationCount() {
        return operationCount;
    }

    public void setOperationCount(int operationCount) {
        this.operationCount = operationCount;
    }

    public int getOriginalDataFlag() {
        return originalDataFlag;
    }

    public void setOriginalDataFlag(int originalDataFlag) {
        this.originalDataFlag = originalDataFlag;
    }

    public int getFlagCorrectedPosition() {
        return flagCorrectedPosition;
    }

    public void setFlagCorrectedPosition(int flagCorrectedPosition) {
        this.flagCorrectedPosition = flagCorrectedPosition;
    }

    public int getFlagDivergentPositionVMS() {
        return flagDivergentPositionVMS;
    }

    public void setFlagDivergentPositionVMS(int flagDivergentPositionVMS) {
        this.flagDivergentPositionVMS = flagDivergentPositionVMS;
    }

    public int getFlagCompatibilityWell() {
        return flagCompatibilityWell;
    }

    public void setFlagCompatibilityWell(int flagCompatibilityWell) {
        this.flagCompatibilityWell = flagCompatibilityWell;
    }

    public int getFlagObserver() {
        return flagObserver;
    }

    public void setFlagObserver(int flagObserver) {
        this.flagObserver = flagObserver;
    }

    public int getFlagExpert() {
        return flagExpert;
    }

    public void setFlagExpert(int flagExpert) {
        this.flagExpert = flagExpert;
    }

    public double getTemperatureSurface() {
        return surfaceTemperature;
    }

    public void setTemperatureSurface(double temperatureSurface) {
        this.surfaceTemperature = temperatureSurface;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }

    public double getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(double currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public double getWindVelocity() {
        return windVelocity;
    }

    public void setWindVelocity(double windVelocity) {
        this.windVelocity = windVelocity;
    }

    public int getFlagEcoFAD() {
        return flagEcoFAD;
    }

    public void setFlagEcoFAD(int flagEcoFAD) {
        this.flagEcoFAD = flagEcoFAD;
    }

    public int getBuoyBelongsVessel() {
        return buoyBelongsVessel;
    }

    public void setBuoyBelongsVessel(int buoyBelongsVessel) {
        this.buoyBelongsVessel = buoyBelongsVessel;
    }

    public String getBuoyId() {
        return buoyId;
    }

    public void setBuoyId(String buoyId) {
        this.buoyId = buoyId;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public double getEstimatedWeight() {
        return estimatedWeight;
    }

    public void setEstimatedWeight(double estimatedWeight) {
        this.estimatedWeight = estimatedWeight;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }

        Activity activite = (Activity) o;

        if (buoyBelongsVessel != activite.buoyBelongsVessel) {
            return false;
        }
        if (codeOcean != activite.codeOcean) {
            return false;
        }
        if (currentDirection != activite.currentDirection) {
            return false;
        }
        if (windDirection != activite.windDirection) {
            return false;
        }
        if (flagCompatibilityWell != activite.flagCompatibilityWell) {
            return false;
        }
        if (flagEcoFAD != activite.flagEcoFAD) {
            return false;
        }
        if (originalDataFlag != activite.originalDataFlag) {
            return false;
        }
        if (flagExpert != activite.flagExpert) {
            return false;
        }
        if (flagObserver != activite.flagObserver) {
            return false;
        }
        if (flagCorrectedPosition != activite.flagCorrectedPosition) {
            return false;
        }
        if (flagDivergentPositionVMS != activite.flagDivergentPositionVMS) {
            return false;
        }
        if (hour != activite.hour) {
            return false;
        }
        if (Float.compare(activite.fishingTime, fishingTime) != 0) {
            return false;
        }
        if (Float.compare(activite.timeAtSea, timeAtSea) != 0) {
            return false;
        }
        if (latitude != activite.latitude) {
            return false;
        }
        if (longitude != activite.longitude) {
            return false;
        }
        if (minute != activite.minute) {
            return false;
        }
        if (operationCount != activite.operationCount) {
            return false;
        }
        if (quadrant != activite.quadrant) {
            return false;
        }
        if (Double.compare(activite.estimatedWeight, estimatedWeight) != 0) {
            return false;
        }
        if (Double.compare(activite.currentVelocity, currentVelocity) != 0) {
            return false;
        }
        if (Double.compare(activite.windVelocity, windVelocity) != 0) {
            return false;
        }
        if (fishingContexts != null ? !fishingContexts.equals(activite.fishingContexts) : activite.fishingContexts != null) {
            return false;
        }

        if (buoyType != null ? !buoyType.equals(activite.buoyType) : activite.buoyType != null) {
            return false;
        }
        if (elementaryCatchs != null ? !elementaryCatchs.equals(activite.elementaryCatchs) : activite.elementaryCatchs != null) {
            return false;
        }
        if (comments != null ? !comments.equals(activite.comments) : activite.comments != null) {
            return false;
        }

        if (buoyId != null ? !buoyId.equals(activite.buoyId) : activite.buoyId != null) {
            return false;
        }

        if (operation != null ? !operation.equals(activite.operation) : activite.operation != null) {
            return false;
        }
        if (Double.compare(activite.catchWeight, catchWeight) != 0) {
            return false;
        }
        if (Double.compare(activite.surfaceTemperature, surfaceTemperature) != 0) {
            return false;
        }
        if (schoolType != null ? !schoolType.equals(activite.schoolType) : activite.schoolType != null) {
            return false;
        }
        return !(logType != null ? !logType.equals(activite.logType) : activite.logType != null);

    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.schoolType);
        hash = 67 * hash + Objects.hashCode(this.logType);
        hash = 67 * hash + Objects.hashCode(this.buoyType);
        hash = 67 * hash + this.hour;
        hash = 67 * hash + this.minute;
        hash = 67 * hash + this.codeOcean;
        hash = 67 * hash + this.quadrant;
        hash = 67 * hash + this.latitude;
        hash = 67 * hash + this.longitude;
        hash = 67 * hash + this.timeAtSea;
        hash = 67 * hash + this.fishingTime;
        hash = 67 * hash + this.operationCount;
        hash = 67 * hash + this.originalDataFlag;
        hash = 67 * hash + this.flagCorrectedPosition;
        hash = 67 * hash + this.flagDivergentPositionVMS;
        hash = 67 * hash + this.flagCompatibilityWell;
        hash = 67 * hash + this.flagObserver;
        hash = 67 * hash + this.flagExpert;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.catchWeight) ^ (Double.doubleToLongBits(this.catchWeight) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.surfaceTemperature) ^ (Double.doubleToLongBits(this.surfaceTemperature) >>> 32));
        hash = 67 * hash + this.currentDirection;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.currentVelocity) ^ (Double.doubleToLongBits(this.currentVelocity) >>> 32));
        hash = 67 * hash + this.windDirection;
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.windVelocity) ^ (Double.doubleToLongBits(this.windVelocity) >>> 32));
        hash = 67 * hash + this.flagEcoFAD;
        hash = 67 * hash + this.buoyBelongsVessel;
        hash = 67 * hash + Objects.hashCode(this.buoyId);
        hash = 67 * hash + Objects.hashCode(this.operation);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.estimatedWeight) ^ (Double.doubleToLongBits(this.estimatedWeight) >>> 32));
        hash = 67 * hash + Objects.hashCode(this.comments);
        return hash;
    }

    @Override
    public String toString() {
        return "Activite{"
                + "trip=" + getVessel().getCode() + "-" + getLandingDate()
                + ", \ndate=" + date
                //                + ", schoolType=" + schoolType
                //                +getLandingDateypeObjet
                + ", number=" + number
                //                + ", \nbaliseOrigine=" + buoyType
                + ", hour=" + hour
                + ", minute=" + minute
                //                + ", codeOcean=" + codeOcean
                //                + ", quadrant=" + quadrant
                //                + ", \nlatitude=" + latitude
                //                + ", longitude=" + longitude
                //                + ", timeAtSea=" + timeAtSea
                //                + ", fishingTime=" + fishingTime
                //                + ", \nnombreOperations=" + operationCount
                //                + ", originalDataFlag=" + originalDataFlag
                //                + ", flagCorrectedPosition=" + flagCorrectedPosition
                //                + ", flagDivergentPositionVMS=" + flagDivergentPositionVMS
                //                + ", flagCompatibilityWell=" + flagCompatibilityWell
                //                + ", \nflagObservateur=" + flagObserver
                //                + ", flagExpert=" + flagExpert
                //                + ", catchWeight=" + catchWeight
                //                + ", surfaceTemperature=" + surfaceTemperature
                //                + ", currentDirection=" + currentDirection
                //                + ", \nvitesseCourant=" + currentVelocity
                //                + ", windDirection=" + windDirection
                //                + ", windVelocity=" + windVelocity
                //                + ", flagEcoFAD=" + flagEcoFAD
                //                + ", \nbaliseAppartientAuBateau=" + buoyBelongsVessel
                //                + //                ", elementaryCatchs=" + elementaryCatchs +
                //                ", buoyId='" + buoyId + '\''
                //                //                + ", fishingContexts=" + fishingContexts
                //                + ", operation=" + operation
                //                + ", estimatedWeight=" + estimatedWeight
                //                + ", comments='" + comments + '\''
                + '}';
    }

    @Override
    public String getID() {
        return String.format("%s %s %s %s", getVessel().getCode(), DATE_FORMATTER.print(landingDate), DATE_FORMATTER.print(this.fullDate), this.number);
    }

    public DateTime getFullDate() {
        return fullDate;
    }

    public void setFullDate(DateTime fullDate) {
        this.fullDate = fullDate;
    }

}
