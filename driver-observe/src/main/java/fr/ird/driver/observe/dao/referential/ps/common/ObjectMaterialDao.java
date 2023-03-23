package fr.ird.driver.observe.dao.referential.ps.common;

import fr.ird.driver.observe.business.referential.ps.common.ObjectMaterial;
import fr.ird.driver.observe.business.referential.ps.common.ObjectMaterialType;
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
public class ObjectMaterialDao extends AbstractI18nReferentialDao<ObjectMaterial> {

    private static final String QUERY = I18N_REFERENTIAL_ENTITY_QUERY + ",\n" +
            /* 18 */ " legacyCode,\n" +
            /* 19 */ " standardCode,\n" +
            /* 20 */ " biodegradable,\n" +
            /* 21 */ " nonEntangling,\n" +
            /* 22 */ " childrenMultiSelectable,\n" +
            /* 23 */ " childSelectionMandatory,\n" +
            /* 24 */ " validation,\n" +
            /* 25 */ " parent,\n" +
            /* 26 */ " objectMaterialType\n" +
            " FROM ps_common.ObjectMaterial";

    public ObjectMaterialDao() {
        super(ObjectMaterial.class, QUERY, ObjectMaterial::new);
    }

    @Override
    protected void fill(ObjectMaterial result, ResultSet rs) throws SQLException, ObserveDriverException {
        super.fill(result, rs);
        result.setLegacyCode(rs.getString(18));
        result.setStandardCode(rs.getString(19));
        result.setBiodegradable((Boolean) rs.getObject(20));
        result.setNonEntangling((Boolean) rs.getObject(21));
        result.setChildrenMultiSelectable(rs.getBoolean(22));
        result.setChildSelectionMandatory(rs.getBoolean(23));
        result.setValidation(rs.getString(24));
        ReferentialCache referentialCache = referentialCache();
        result.setParent(referentialCache.lazyReferential(ObjectMaterial.class, rs.getString(25)));
        result.setObjectMaterialType(referentialCache.lazyReferential(ObjectMaterialType.class, rs.getString(26)));
    }
}
