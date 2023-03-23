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

import fr.ird.common.log.LogService;

/**
 * Les catégories de taille.
 */
public class SizeCategory {

    public static int SIZE_CATEGORY_UNKNOWN = 9;

    private Species species;
    private Integer code;
    private String name;
    private Float averageWeight;
    private Boolean status = true;

    public Boolean getStatus() {
        if (status == null) {
            LogService.getService(SizeCategory.class).logApplicationDebug("[CAT_TAILLE] The status is null.");
        }
        return status;
    }

    public String getName() {
        if (name == null) {
            LogService.getService(SizeCategory.class).logApplicationDebug("[CAT_TAILLE] The name is null.");
        }
        return name;
    }

    public Float getAverageWeight() {

        return averageWeight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setAverageWeight(Float averageWeight) {
        this.averageWeight = averageWeight;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "SizeCategory{" + "espece=" + species + ", code=" + code + ", name=" + name + ", averageWeight=" + averageWeight + ", status=" + status + '}';
    }

}
