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
import fr.ird.driver.avdth.business.local.market.Packaging;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.AbstractDAO;
import fr.ird.driver.avdth.dao.HarbourDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table
 * <em>FP_CONDITIONNEMENT</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class PackagingDAO extends AbstractDAO<Packaging> {

    @Override
    public boolean insert(Packaging t) {
        throw new UnsupportedOperationException("Not supported because the «Packaging» class  represents a referentiel.");
    }

    @Override
    public boolean delete(Packaging t) {
        throw new UnsupportedOperationException("Not supported because the «Packaging» class  represents a referentiel.");
    }

    @Override
    protected Packaging factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Packaging packaging = new Packaging();
        packaging.setCode(rs.getInt("C_COND"));
        packaging.setHarbour((new HarbourDAO()).findHarbourByCode(rs.getInt("C_PORT")));
        packaging.setStartDate(DateTimeUtils.convertDate(rs.getDate("D_DEBUT")));
        packaging.setEndDate(DateTimeUtils.convertDate(rs.getDate("D_FIN")));
        packaging.setName(rs.getString("L_COND"));
        packaging.setPackagingType((new PackagingTypeDAO()).findPackagingTypeByCode(rs.getInt("C_TYP_CON")));
        packaging.setWeight(rs.getDouble("V_POIDS"));
        return packaging;
    }

    public Packaging findPackagingByCode(int code) throws AvdthDriverException {
        Packaging packaging = null;
        String query = "select * from FP_CONDITIONNEMENT "
                + " where C_COND = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                packaging = factory(rs);
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
        return packaging;
    }

}
