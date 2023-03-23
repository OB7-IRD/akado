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
 * Les codes associations.
 */
public class FishingContextType {

    public static final int INCONNU = 0;
    public static final int EPAVE_NATURELLE = 21;
    public static final int EPAVE_ARTIFICIELLE = 23;
    public static final int BATEAU_ASSISTANCE = 28;
    public static final int OISEAU = 30;
    public static final int EPAVE_NATURELLE_BALISEE = 22;
    public static final int EPAVE_ARTIFICIELLE_BALISEE = 24;
    public static final int REQUIN_BALEINE = 60;
    public static final int BALEINE = 51;
    public static int FISHING_CONTEXT_GROUP_FAD = 1;
    public static int FISHING_CONTEXT_GROUP_FSC = 2;
    public static int FISHING_CONTEXT_GROUP_UNDEFINED = 3;

    /**
     * Code numérique de l'association
     *
     * @pdOid 0687f7ea-70d6-4eb5-9ad5-af7fd4258196
     */
    private int code;
    /**
     * Libellé de l'association
     *
     * @pdOid 7924f4bb-5ee2-408c-b977-cf8ceb0c307d
     */
    private java.lang.String name;
    /**
     * @pdOid 8fc6b076-580a-4d53-a1dc-e4a91991f4e7
     */
    private int rCode;
    /**
     * @pdOid 38b87d52-8675-4c22-8e98-e76477e65e0a
     */
    private int groupCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public int getRCode() {
        return rCode;
    }

    public void setRCode(int rCode) {
        this.rCode = rCode;
    }

    public int getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(int groupCode) {
        this.groupCode = groupCode;
    }

    @Override
    public String toString() {
        return "FishingContextType{" + "code=" + code + ", name=" + name + ", rCode=" + rCode + ", groupCode=" + groupCode + '}';
    }

    
}
