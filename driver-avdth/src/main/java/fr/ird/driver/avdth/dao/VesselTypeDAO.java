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
import fr.ird.driver.avdth.business.VesselType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table TYPE_BATEAU.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class VesselTypeDAO extends AbstractDAO<VesselType> {
    private static final Logger log = LogManager.getLogger(VesselTypeDAO.class);
    public VesselTypeDAO() {
        super();
    }

    public List<VesselType> getAllVesselType() {
        ArrayList<VesselType> vesselTypes = null;

        String query = "select * from TYPE_BATEAU ";
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            vesselTypes = new ArrayList<VesselType>();
            while (rs.next()) {
                vesselTypes.add(factory(rs));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            log.error(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return vesselTypes;

    }

    public VesselType findVesselTypeByCode(int code) {
        VesselType typeBateau = null;

        String query = "select * from TYPE_BATEAU "
                + " where C_TYP_B = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                typeBateau = factory(rs);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            log.error(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return typeBateau;
    }

    @Override
    public boolean insert(VesselType t) {
        throw new UnsupportedOperationException("Not supported because the «VesselType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(VesselType t) {
        throw new UnsupportedOperationException("Not supported because the «VesselType» class  represents a referentiel.");
    }

    @Override
    protected VesselType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        VesselType typeBateau = new VesselType();
        typeBateau.setCode(rs.getInt("C_TYP_B"));
        typeBateau.setName(rs.getString("L_TYP_B"));

        VesselSimpleTypeDAO typeTypeBateauDAO = new VesselSimpleTypeDAO();
        typeBateau.setSimpleType(
                typeTypeBateauDAO.findVesselSimpleTypeByCode(rs.getInt("C_TYP_TYP_B")));
        return typeBateau;
    }

}
