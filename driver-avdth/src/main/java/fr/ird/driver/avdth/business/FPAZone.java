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
package fr.ird.driver.avdth.business;

/**
 * Fishing Partner Agreements. This class name in AVDTH is ZONE_FPA.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class FPAZone {

    private int code;
    private String name;
    private Country country;
    private String subdiv;
    private boolean status;

    public FPAZone() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getSubdiv() {
        return subdiv;
    }

    public void setSubdiv(String subdiv) {
        this.subdiv = subdiv;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FPAZone{" + "code=" + code + ", name=" + name + ", country=" + country + ", subdiv=" + subdiv + ", status=" + status + '}';
    }

}
