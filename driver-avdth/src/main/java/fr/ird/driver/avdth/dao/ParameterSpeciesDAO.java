package fr.ird.driver.avdth.dao;

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
import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.ParameterSpecies;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO to make queries on the table <em>ESPECE_PARAMETRE</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class ParameterSpeciesDAO extends AbstractDAO<ParameterSpecies> {

    public ParameterSpeciesDAO() {
        super();
    }

    @Override
    public boolean insert(ParameterSpecies t) {
        throw new UnsupportedOperationException("Not supported because the «ParameterSpecies» class  represents a referentiel.");
    }

    @Override
    public boolean delete(ParameterSpecies t) {
        throw new UnsupportedOperationException("Not supported because the «ParameterSpecies» class  represents a referentiel.");
    }

    public ParameterSpecies findParameterSpeciesByCode(int code) throws AvdthDriverException {
        ParameterSpecies parameterSpecies = null;
        PreparedStatement statement = null;
        try {
            String query = "select * from ESPECE_PARAMETRAGE "
                    + " where C_PARAM = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                parameterSpecies = factory(rs);
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
        return parameterSpecies;
    }

    @Override
    protected ParameterSpecies factory(ResultSet rs) throws SQLException, AvdthDriverException {
        ParameterSpecies parameterSpecies = new ParameterSpecies();
        parameterSpecies.setCode(rs.getInt("C_PARAM"));
        SpeciesDAO sdao = new SpeciesDAO();
        parameterSpecies.setSpecies(sdao.findSpeciesByCode(rs.getInt("C_ESP")));
        parameterSpecies.setOcean((new OceanDAO()).findOceanByCode(rs.getInt("C_OCEA")));
        parameterSpecies.setStartDate(DateTimeUtils.convertDate(rs.getDate("D_DEBUT")));
        parameterSpecies.setEndDate(DateTimeUtils.convertDate(rs.getDate("D_FIn")));
        parameterSpecies.setAverageWeight(rs.getFloat("V_POIDS_MOY"));
        parameterSpecies.setA(rs.getFloat("V_A"));
        parameterSpecies.setB(rs.getFloat("V_B"));
        parameterSpecies.setComments(rs.getString("L_COM"));
        return parameterSpecies;
    }
}
