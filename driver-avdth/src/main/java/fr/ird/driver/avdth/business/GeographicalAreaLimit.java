/* 
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
 */
package fr.ird.driver.avdth.business;

/**
 * *********************************************************************
 * Module: LimitesZoneGeo.java Author: Julien Lebranchu Purpose: Defines the
 * Class LimitesZoneGeo -- Classe référentiel
 * *********************************************************************
 */
/**
 * Zones géo. constituée de 1 ou plusieurs rectangles - hors zone ((1i99, 2i99),
 * i = 1,3)
 *
 *
 */
public class GeographicalAreaLimit {

    private GeographicalArea geographicalArea;
    /**
     * indice du rectangle (1 à n) dans la zone
     *
     * @pdOid c366ee92-457a-4471-ae62-f73db8167044
     */
    private int number;
    /**
     * 1 : o. atlantique, 2 : o. indien libres Redondant mais améliorera les
     * performances de l'algo. de détermination
     *
     * @pdOid 1f6d4ee1-ccd8-40f9-a54b-ebee88ff8de4
     */
    private Ocean ocean;
    /**
     * 1 : épaves, 2 : bancs libres, 3 : indéterminé Redondant mais améliorera
     * les performances de l'algo. de détermination
     *
     * @pdOid 557a64d6-8458-48c9-9a51-265fb0b8cc9a
     */
    private int zoneType;
    /**
     * Quadrant du rectangle - utile à l'algo de détermination Quadrant de la
     * nomenclature CWP
     *
     * @pdOid 510da509-0a14-47c2-8782-5a6112d7bfb7
     */
    private int quadrant;
    /**
     * Latitude en degrés, coordonnée négative au sud
     *
     * @pdOid 280ed7e7-f86f-4434-9467-365e810f7b5f
     */
    private int minLatitude;
    /**
     * Latitude en degrés, coordonnée négative au sud
     *
     * @pdOid 2460321b-78d4-46f0-8617-dc1988549fce
     */
    private int maxLatitude;
    /**
     * Longitude en degrés, coordonnée négative à l'ouest.
     *
     * @pdOid bdd1a041-c5e1-439c-be05-aa9493032c32
     */
    private int minLongitude;
    /**
     * Longitude en degrés, coordonnée négative à l'ouest.
     *
     * @pdOid 98e5e85e-e993-4782-a83c-fab083d1703f
     */
    private int maxLongitude;

    public Ocean getOcean() {
        return ocean;
    }

    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

    public int getZoneType() {
        return zoneType;
    }

    public void setZoneType(int zoneType) {
        this.zoneType = zoneType;
    }

    public int getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    public int getMinLatitude() {
        return minLatitude;
    }

    public void setMinLatitude(int minLatitude) {
        this.minLatitude = minLatitude;
    }

    public int getMaxLatitude() {
        return maxLatitude;
    }

    public void setMaxLatitude(int maxLatitude) {
        this.maxLatitude = maxLatitude;
    }

    public int getMinLongitude() {
        return minLongitude;
    }

    public void setMinLongitude(int minLongitude) {
        this.minLongitude = minLongitude;
    }

    public int getMaxLongitude() {
        return maxLongitude;
    }

    public void setMaxLongitude(int maxLongitude) {
        this.maxLongitude = maxLongitude;
    }

    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
