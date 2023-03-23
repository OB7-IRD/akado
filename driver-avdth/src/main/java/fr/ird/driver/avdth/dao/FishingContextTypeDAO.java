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
import fr.ird.driver.avdth.business.FishingContextType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table <em>ASSOC</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class FishingContextTypeDAO extends AbstractDAO<FishingContextType> {

    @Override
    public boolean insert(FishingContextType t) {
        throw new UnsupportedOperationException("Not supported because the «FishingContextType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(FishingContextType t) {
        throw new UnsupportedOperationException("Not supported because the «FishingContextType» class  represents a referentiel.");
    }

    @Override
    protected FishingContextType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        FishingContextType type = new FishingContextType();
        type.setCode(rs.getInt("C_ASSOC"));
        type.setName(rs.getString("L_ASSOC"));
        type.setGroupCode(rs.getInt("C_ASSOC_G"));
        type.setRCode(rs.getInt("C_ASSOC_R"));
        return type;
    }

    public FishingContextType findFishingContextTypeByCode(int code) throws AvdthDriverException {
        FishingContextType type = null;
        PreparedStatement statement = null;
        try {
            String query = "select * from ASSOC "
                    + " where C_ASSOC = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                type = factory(rs);
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return type;
    }

    public List<FishingContextType> findFishingContextTypeByComment(String contextFishing) throws AvdthDriverException {
        ArrayList<FishingContextType> fishingContextTypes = null;
        PreparedStatement statement = null;

        String query = "select * from ASSOC "
                + " where L_ASSOC like ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + contextFishing + "%");
            ResultSet rs = statement.executeQuery();
            fishingContextTypes = new ArrayList<>();
            FishingContextType contextType = null;
            while (rs.next()) {
                contextType = factory(rs);
                fishingContextTypes.add(contextType);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return fishingContextTypes;

    }

    public FishingContextType findFirstFishingContextTypeByComment(String contextFishing) throws AvdthDriverException {
        List<FishingContextType> associations = findFishingContextTypeByComment(contextFishing);
        if (associations == null || associations.isEmpty()) {
            return null;
        }
        return associations.get(0);

    }
}
