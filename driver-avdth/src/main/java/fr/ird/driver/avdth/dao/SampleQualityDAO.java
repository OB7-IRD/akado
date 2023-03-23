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
import fr.ird.driver.avdth.business.SampleQuality;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table QUAL_ECH.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class SampleQualityDAO extends AbstractDAO<SampleQuality> {

    public SampleQualityDAO() {
        super();
    }

    @Override
    public boolean insert(SampleQuality t) {
        throw new UnsupportedOperationException("Not supported because the «SampleQuality» class  represents a referentiel.");
    }

    @Override
    public boolean delete(SampleQuality t) {
        throw new UnsupportedOperationException("Not supported because the «SampleQuality» class  represents a referentiel.");
    }

    public SampleQuality findSampleQualityByCode(int code) {
        SampleQuality sampleQuality = null;
        String query = "select * from QUAL_ECH "
                + " where C_QUAL_ECH = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampleQuality = factory(rs);
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
        return sampleQuality;
    }

    @Override
    public SampleQuality factory(ResultSet rs) throws SQLException, AvdthDriverException {
        SampleQuality sampleQuality = new SampleQuality();
        sampleQuality.setCode(rs.getInt("C_QUAL_ECH"));
        sampleQuality.setName(rs.getString("L_QUAL_ECH"));
        return sampleQuality;
    }

}
