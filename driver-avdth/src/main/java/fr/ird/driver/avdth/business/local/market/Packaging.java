/*
 * Copyright (C) 2016 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.business.local.market;

import fr.ird.driver.avdth.business.Harbour;
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is FP_CONDITIONNEMENT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class Packaging {

    private int code;
    private Harbour harbour;
    private DateTime startDate;
    private DateTime endDate;
    private String name;
    private PackagingType packagingType;
    private double weight;

    @Override
    public String toString() {
        return "Packaging{" + "code=" + code + ", harbour=" + harbour + ", startDate=" + startDate + ", endDate=" + endDate + ", name=" + name + ", packagingType=" + packagingType + ", weight=" + weight + '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Harbour getHarbour() {
        return harbour;
    }

    public void setHarbour(Harbour harbour) {
        this.harbour = harbour;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PackagingType getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(PackagingType packagingType) {
        this.packagingType = packagingType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    
}
