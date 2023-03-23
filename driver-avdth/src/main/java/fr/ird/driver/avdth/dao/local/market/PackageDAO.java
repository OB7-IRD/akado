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
package fr.ird.driver.avdth.dao.local.market;

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.local.market.Package;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.AbstractDAO;
import fr.ird.driver.avdth.dao.SpeciesDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>FP_LOT</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class PackageDAO extends AbstractDAO<Package> {

    @Override
    public boolean insert(Package p) {
        PreparedStatement statement = null;
        String query = "insert into FP_LOT "
                + " (C_BAT, D_DBQ, N_LOT, C_ESP, C_COND, "
                + " N_UNIT, V_POIDS_PESE,L_ORIGINE, L_COM) "
                + " values (?, ?, ?,  ?, ?, ?, ?, ?, ?);";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, p.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(p.getLandingDate()));
            statement.setString(3, p.getNumber());

            int code = -1;
            if (p.getSpecies() != null) {
                code = p.getSpecies().getCode();
            }
            statement.setInt(4, code);

            code = -1;
            if (p.getPackaging() != null) {
                code = p.getPackaging().getCode();
            }
            statement.setInt(5, code);

            statement.setDouble(6, p.getUnit());
            statement.setDouble(7, p.getWeightedWeight());
            statement.setString(8, p.getOrigin());
            statement.setString(9, p.getComments());
            statement.execute();
            statement.close();

            connection.commit();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
            }
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Package p) {
        PreparedStatement statement = null;
        String query = "delete from FP_LOT "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and N_LOT = ? ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, p.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(p.getLandingDate()));
            statement.setString(3, p.getNumber());

            statement.execute();
            statement.close();

            connection.commit();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
            }
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
                return false;
            }
        }
        return true;
    }

    @Override
    protected Package factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Package p = new Package();
        VesselDAO vdao = new VesselDAO();
        p.setVessel(vdao.findVesselByCode(rs.getInt("C_BAT")));
        p.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        p.setNumber(rs.getString("N_LOT"));
        SpeciesDAO sdao = new SpeciesDAO();
        p.setSpecies(sdao.findSpeciesByCode(rs.getInt("C_ESP")));
        PackagingDAO pdao = new PackagingDAO();
        p.setPackaging(pdao.findPackagingByCode(rs.getInt("C_COND")));
        p.setUnit(rs.getFloat("N_UNIT"));
        p.setWeightedWeight(0);
        p.setOrigin(rs.getString("L_ORIGINE"));
        p.setComments(rs.getString("L_COM"));
        return p;
    }

}
