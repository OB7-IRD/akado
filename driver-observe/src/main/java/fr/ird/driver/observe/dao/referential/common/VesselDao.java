package fr.ird.driver.observe.dao.referential.common;

import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.business.referential.common.ShipOwner;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.common.VesselSizeCategory;
import fr.ird.driver.observe.business.referential.common.VesselType;
import fr.ird.driver.observe.common.ObserveDriverException;
import fr.ird.driver.observe.dao.referential.AbstractI18nReferentialDao;
import fr.ird.driver.observe.dao.referential.ReferentialCache;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VesselDao extends AbstractI18nReferentialDao<Vessel> {
    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " keelCode,\n" +
            /* 19 */ " changeDate,\n" +
            /* 20 */ " yearService,\n" +
            /* 21 */ " length,\n" +
            /* 22 */ " capacity,\n" +
            /* 23 */ " powerCv,\n" +
            /* 24 */ " powerKW,\n" +
            /* 25 */ " searchMaximum,\n" +
            /* 26 */ " comment,\n" +
            /* 27 */ " source,\n" +
            /* 28 */ " iccatId,\n" +
            /* 29 */ " iotcId,\n" +
            /* 30 */ " nationalId,\n" +
            /* 31 */ " tuviId,\n" +
            /* 32 */ " imoId,\n" +
            /* 33 */ " radioCallSignId,\n" +
            /* 34 */ " lloydId,\n" +
            /* 35 */ " cfrId,\n" +
            /* 36 */ " wellRegex,\n" +
            /* 37 */ " startDate,\n" +
            /* 38 */ " endDate,\n" +
            /* 39 */ " vesselSizeCategory,\n" +
            /* 40 */ " vesselType,\n" +
            /* 41 */ " flagCountry,\n" +
            /* 42 */ " fleetCountry,\n" +
            /* 43 */ " shipOwner\n" +
            " FROM common.Vessel";

    public VesselDao() {
        super(Vessel.class, QUERY, Vessel::new);
    }

    @Override
    protected void fill(Vessel result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setKeelCode(getInteger(rs, 18));
        result.setChangeDate(rs.getDate(19));
        result.setYearService(getInteger(rs, 20));
        result.setLength(rs.getDouble(21));
        result.setCapacity(rs.getDouble(22));
        result.setPowerCv(getInteger(rs, 23));
        result.setPowerKW(getInteger(rs, 24));
        result.setSearchMaximum(rs.getDouble(25));
        result.setComment(rs.getString(26));
        result.setSource(rs.getString(27));
        result.setIccatId(rs.getString(28));
        result.setIotcId(rs.getString(29));
        result.setNationalId(rs.getString(30));
        result.setTuviId(rs.getString(31));
        result.setImoId(rs.getString(32));
        result.setRadioCallSignId(rs.getString(33));
        result.setLloydId(rs.getString(34));
        result.setCfrId(rs.getString(35));
        result.setWellRegex(rs.getString(36));
        result.setStartDate(rs.getDate(37));
        result.setEndDate(rs.getDate(38));
        ReferentialCache referentialCache = referentialCache();
        result.setVesselSizeCategory(referentialCache.lazyReferential(VesselSizeCategory.class, rs.getString(39)));
        result.setVesselType(referentialCache.lazyReferential(VesselType.class, rs.getString(40)));
        result.setFlagCountry(referentialCache.lazyReferential(Country.class, rs.getString(41)));
        result.setFleetCountry(referentialCache.lazyReferential(Country.class, rs.getString(42)));
        result.setShipOwner(referentialCache.lazyReferential(ShipOwner.class, rs.getString(43)));
    }
}
