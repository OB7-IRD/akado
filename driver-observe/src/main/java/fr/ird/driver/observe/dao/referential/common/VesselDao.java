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
            /* 31 */ " comId,\n" +
            /* 32 */ " tuviId,\n" +
            /* 33 */ " imoId,\n" +
            /* 34 */ " radioCallSignId,\n" +
            /* 35 */ " lloydId,\n" +
            /* 36 */ " cfrId,\n" +
            /* 37 */ " wellRegex,\n" +
            /* 38 */ " startDate,\n" +
            /* 39 */ " endDate,\n" +
            /* 40 */ " vesselSizeCategory,\n" +
            /* 41 */ " vesselType,\n" +
            /* 42 */ " flagCountry,\n" +
            /* 43 */ " fleetCountry,\n" +
            /* 44 */ " shipOwner\n" +
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
        result.setLength(getFloat(rs, 21));
        result.setCapacity(getFloat(rs, 22));
        result.setPowerCv(getInteger(rs, 23));
        result.setPowerKW(getInteger(rs, 24));
        result.setSearchMaximum(getFloat(rs, 25));
        result.setComment(rs.getString(26));
        result.setSource(rs.getString(27));
        result.setIccatId(rs.getString(28));
        result.setIotcId(rs.getString(29));
        result.setNationalId(rs.getString(30));
        result.setComId(rs.getString(31));
        result.setTuviId(rs.getString(32));
        result.setImoId(rs.getString(33));
        result.setRadioCallSignId(rs.getString(34));
        result.setLloydId(rs.getString(35));
        result.setCfrId(rs.getString(36));
        result.setWellRegex(rs.getString(37));
        result.setStartDate(rs.getDate(38));
        result.setEndDate(rs.getDate(39));
        ReferentialCache referentialCache = referentialCache();
        result.setVesselSizeCategory(referentialCache.lazyReferential(VesselSizeCategory.class, rs.getString(40)));
        result.setVesselType(referentialCache.lazyReferential(VesselType.class, rs.getString(41)));
        result.setFlagCountry(referentialCache.lazyReferential(Country.class, rs.getString(42)));
        result.setFleetCountry(referentialCache.lazyReferential(Country.class, rs.getString(43)));
        result.setShipOwner(referentialCache.lazyReferential(ShipOwner.class, rs.getString(44)));
    }
}
