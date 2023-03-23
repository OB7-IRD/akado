package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.GearCharacteristic;
import fr.ird.driver.observe.business.referential.common.GearCharacteristicType;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class GearCharacteristicDao extends AbstractI18nReferentialDao<GearCharacteristic> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " unit,\n" +
            /* 19 */ " gearCharacteristicType\n" +
            " FROM common.GearCharacteristic";

    public GearCharacteristicDao() {
        super(GearCharacteristic.class, QUERY, GearCharacteristic::new);
    }

    @Override
    protected void fill(GearCharacteristic result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setUnit(rs.getString(18));
        result.setGearCharacteristicType(referentialCache().lazyReferential(GearCharacteristicType.class, rs.getString(19)));
    }
}
