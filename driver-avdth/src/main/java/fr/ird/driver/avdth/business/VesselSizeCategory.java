/* $Id: VesselSizeCategory.java 385 2014-07-11 10:17:43Z lebranch $
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

import java.util.Collection;
import java.util.Iterator;

/**
 * Les capacités de transport des bateaux (jauge).
 *
 * @pdOid 6b568316-c1b5-4977-abb3-b075a4436383
 *
 * $LastChangedDate: 2014-07-11 12:17:43 +0200 (ven. 11 juil. 2014) $
 *
 * $LastChangedRevision: 385 $
 */
public class VesselSizeCategory {

    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

    /* (non-Javadoc)
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
//        result = prime * result + ((bateau == null) ? 0 : bateau.hashCode());
        result = prime * result + codeCategorieBateau;
        result = prime * result
                + ((libelleCapacite == null) ? 0 : libelleCapacite.hashCode());
        result = prime * result
                + ((libelleJauge == null) ? 0 : libelleJauge.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        VesselSizeCategory other = (VesselSizeCategory) obj;
//        if (bateau == null) {
//            if (other.bateau != null) {
//                return false;
//            }
//        } else if (!bateau.equals(other.bateau)) {
//            return false;
//        }
        if (codeCategorieBateau != other.codeCategorieBateau) {
            return false;
        }
        if (libelleCapacite == null) {
            if (other.libelleCapacite != null) {
                return false;
            }
        } else if (!libelleCapacite.equals(other.libelleCapacite)) {
            return false;
        }
        if (libelleJauge == null) {
            if (other.libelleJauge != null) {
                return false;
            }
        } else if (!libelleJauge.equals(other.libelleJauge)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CategorieBateau{"
                + "codeCategorieBateau=" + codeCategorieBateau
                + ", libelleJauge='" + libelleJauge + '\''
                + ", libelleCapacite='" + libelleCapacite + '\''
                + '}';
    }
    /**
     * Code numérique de la catégorie de bateau
     *
     * @pdOid 6025c2ef-0eeb-432f-894e-18b4ebbdd3ff
     */
    private int codeCategorieBateau;
    /**
     * Jauge exprimée en tonneaux
     *
     * @pdOid 907dd313-94a8-4f98-9d85-13f4ef0a1be9
     */
    private String libelleJauge;
    /**
     * Capacité exprimée en tonnes
     *
     * @pdOid 390ced86-10d5-4b0b-9637-ff8279e05e5d
     */
    private String libelleCapacite;

    public int getCodeCategorieBateau() {
        return codeCategorieBateau;
    }

    public void setCodeCategorieBateau(int codeCategorieBateau) {
        this.codeCategorieBateau = codeCategorieBateau;
    }

    public String getLibelleJauge() {
        return libelleJauge;
    }

    public void setLibelleJauge(String libelleJauge) {
        this.libelleJauge = libelleJauge;
    }

    public String getLibelleCapacite() {
        return libelleCapacite;
    }

    public void setLibelleCapacite(String libelleCapacite) {
        this.libelleCapacite = libelleCapacite;
    }
}
