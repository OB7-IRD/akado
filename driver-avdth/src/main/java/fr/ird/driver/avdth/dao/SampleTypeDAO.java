/* 
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
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
import fr.ird.driver.avdth.business.SampleType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table TYPE_ECHANT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 *
 */
public class SampleTypeDAO extends AbstractDAO<SampleType> {

    public SampleTypeDAO() {
        super();
    }

    @Override
    public boolean insert(SampleType t) {
        throw new UnsupportedOperationException("Not supported because the «SampleType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(SampleType t) {
        throw new UnsupportedOperationException("Not supported because the «SampleType» class  represents a referentiel.");
    }

    public SampleType findSampleTypeByCode(int code) {
        SampleType sampleType = null;
        String query = "select * from TYPE_ECHANT "
                + " where C_TYP_ECH = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampleType = factory(rs);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return sampleType;
    }

    @Override
    public SampleType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        SampleType sampleType = new SampleType();
        sampleType.setCode(rs.getInt("C_TYP_ECH"));
        sampleType.setLibelle(rs.getString("L_TYP_ECH"));
        return sampleType;
    }

}
