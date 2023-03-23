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
 * Defines the Class WeightCategoryLanding. This class name in AVDTH is
 * CategorieCommerciale.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 3.4.4
 * @date 11 déc. 2013
 */
public class WeightCategoryLanding {

    public static int convertFromSizeCategory(int speciesCode, int sizeCategoryCode) {
        switch (speciesCode) {
            case 1:
                //YFT -10kg
                if (sizeCategoryCode == 1
                        || sizeCategoryCode == 2
                        || sizeCategoryCode == 10) {
                    return 1;
                }//YFT +10kg
                else if (sizeCategoryCode == 3
                        || sizeCategoryCode == 5
                        || sizeCategoryCode == 6
                        || sizeCategoryCode == 7
                        || sizeCategoryCode == 8
                        || sizeCategoryCode == 11
                        || sizeCategoryCode == 12
                        || sizeCategoryCode == 13) {
                    return 2;
                }
                return 9;
            case 2:
                if (sizeCategoryCode == 1
                        || sizeCategoryCode == 3
                        || sizeCategoryCode == 4
                        || sizeCategoryCode == 5
                        || sizeCategoryCode == 6
                        || sizeCategoryCode == 7
                        || sizeCategoryCode == 8) {
                    return 1;
                } else if (sizeCategoryCode == 2) {
                    return 2;
                }
                return 9;
            case 3:
                if (sizeCategoryCode == 1
                        || sizeCategoryCode == 2) {
                    return 1;
                } else if (sizeCategoryCode == 3
                        || sizeCategoryCode == 5
                        || sizeCategoryCode == 6
                        || sizeCategoryCode == 7
                        || sizeCategoryCode == 8) {
                    return 2;
                }
                return 9;
            case 4:
                if (sizeCategoryCode == 1
                        || sizeCategoryCode == 2
                        || sizeCategoryCode == 3
                        || sizeCategoryCode == 4
                        || sizeCategoryCode == 5
                        || sizeCategoryCode == 6
                        || sizeCategoryCode == 7
                        || sizeCategoryCode == 8
                        || sizeCategoryCode == 10
                        || sizeCategoryCode == 11
                        || sizeCategoryCode == 12
                        || sizeCategoryCode == 13) {
                    return 2;
                }
                return 9;
            case 5:
                return 9;
            case 6:
                return 9;
            case 7:
                return 9;
            case 8:
                return 9;
            case 9:
                if (sizeCategoryCode == 1
                        || sizeCategoryCode == 2
                        || sizeCategoryCode == 3) {
                    return 1;
                } else if (sizeCategoryCode == 4) {
                    return 2;
                }
                return 9;
            case 10:
                return 9;
            case 11:
                return 9;
            case 12:
                return 9;
            default:
                break;
        }
        return 9;
    }

    private Species species;
    private Integer code;
    private String sovName;
    private String starName;
    private Boolean status = true;

    public Boolean getStatus() {
        if (status == null) {
            System.out.println("[CAT_TAILLE] Le statut est nul");
        }
        return status;
    }

    public String getSovName() {
        if (sovName == null) {
            System.out.println("[CAT_COM] Le libelleCCSov est nul");
        }
        return sovName;
    }

    public String getStarName() {
        if (starName == null) {
            System.out.println("[CAT_COM] Le libelleCCStar est nul");
        }
        return starName;
    }

    public void setSovName(String sovName) {
        this.sovName = sovName;
    }

    public void setStarName(String starName) {
        this.starName = starName;
    }

    public Integer getCode() {
        return code;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CategorieCommercial{" + "espece=" + species + ", codeCategorieCommerciale=" + code + ", libelleCCSov=" + sovName + ", libelleCCStar=" + starName + ", statut=" + status + '}';
    }
}
