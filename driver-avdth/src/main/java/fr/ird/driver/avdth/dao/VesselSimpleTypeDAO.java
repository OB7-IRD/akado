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
package fr.ird.driver.avdth.dao;

import fr.ird.common.JDBCUtilities;
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.VesselSimpleType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table TYPE_TYPE_BATEAU.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class VesselSimpleTypeDAO extends AbstractDAO<VesselSimpleType> {

    public VesselSimpleTypeDAO() {
        super();

    }

    public List<VesselSimpleType> getAllVesselSimpleType() {
        ArrayList<VesselSimpleType> list = null;
        Statement stmt = null;
        String query = "select * from TYPE_TYPE_BATEAU ";

        try {

            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            list = new ArrayList<VesselSimpleType>();
            while (rs.next()) {

                list.add(factory(rs));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return list;
    }

    public VesselSimpleType findVesselSimpleTypeByCode(int code) {
        VesselSimpleType type = null;

        String query = "select * from TYPE_TYPE_BATEAU "
                + " where C_TYP_TYPE_B = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, code);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                type = factory(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return type;
    }

    @Override
    public boolean insert(VesselSimpleType t) {
        throw new UnsupportedOperationException("Not supported because the «VesselSimpleType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(VesselSimpleType t) {
        throw new UnsupportedOperationException("Not supported because the «VesselSimpleType» class  represents a referentiel.");
    }

    @Override
    protected VesselSimpleType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        VesselSimpleType vesselSimpleType = new VesselSimpleType();
        vesselSimpleType.setCode(rs.getInt("C_TYP_TYPE_B"));
        vesselSimpleType.setName(rs.getString("L_TYPE_TYPE_B"));
        vesselSimpleType.setFaoIsscfg(rs.getString("C_FAO_ISSCFG"));
        return vesselSimpleType;
    }
}
