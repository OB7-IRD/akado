package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Gear;
import fr.ird.driver.observe.business.referential.common.GearCharacteristic;
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
public class GearDao extends AbstractI18nReferentialDao<Gear> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + "\n" +
            " FROM common.Gear";

    public GearDao() {
        super(Gear.class, QUERY, Gear::new);
    }

    @Override
    protected void fill(Gear result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setGearCharacteristic(lazyReferentialSet(result.getTopiaId(), "common", "gear_gearCharacteristic", "gear", "gearCharacteristic", GearCharacteristic.class));
    }
}
