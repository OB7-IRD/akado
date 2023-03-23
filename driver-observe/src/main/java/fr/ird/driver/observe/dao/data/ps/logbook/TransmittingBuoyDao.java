package fr.ird.driver.observe.dao.data.ps.logbook;

import fr.ird.driver.observe.business.data.ps.logbook.TransmittingBuoy;
import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyOperation;
import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyOwnership;
import fr.ird.driver.observe.business.referential.ps.common.TransmittingBuoyType;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.data.AbstractDataDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TransmittingBuoyDao extends AbstractDataDao<TransmittingBuoy> {

    private static final String QUERY = ENTITY_QUERY + ",\n" +
            /* 06 */ " comment,\n" +
            /* 07 */ " code,\n" +
            /* 08 */ " latitude,\n" +
            /* 09 */ " longitude,\n" +
            /* 10 */ " transmittingBuoyOwnership,\n" +
            /* 11 */ " transmittingBuoyType,\n" +
            /* 12 */ " transmittingBuoyOperation,\n" +
            /* 13 */ " country,\n" +
            /* 14 */ " vessel\n" +
            " FROM ps_logbook.TransmittingBuoy main WHERE ";
    private static final String BY_PARENT = "main.floatingObject = ? ORDER BY main.topiaCreateDate";

    public TransmittingBuoyDao() {
        super(TransmittingBuoy.class, TransmittingBuoy::new, QUERY, BY_PARENT);
    }

    @Override
    protected void fill(TransmittingBuoy result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setComment(rs.getString(6));
        result.setCode(rs.getString(7));
        result.setLatitude(getFloat(rs, 8));
        result.setLongitude(getFloat(rs, 9));
        ReferentialCache referentialCache = referentialCache();
        result.setTransmittingBuoyOwnership(referentialCache.lazyReferential(TransmittingBuoyOwnership.class, rs.getString(10)));
        result.setTransmittingBuoyType(referentialCache.lazyReferential(TransmittingBuoyType.class, rs.getString(11)));
        result.setTransmittingBuoyOperation(referentialCache.lazyReferential(TransmittingBuoyOperation.class, rs.getString(12)));
        result.setCountry(referentialCache.lazyReferential(Country.class, rs.getString(13)));
        result.setVessel(referentialCache.lazyReferential(Vessel.class, rs.getString(14)));
    }
}
