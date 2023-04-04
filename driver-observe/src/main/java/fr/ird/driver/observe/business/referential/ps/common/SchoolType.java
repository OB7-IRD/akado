package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Objects;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SchoolType extends I18nReferentialEntity {

    public static final String ARTIFICIAL_SCHOOL = "1";
    public static final String FREE_SCHOOL = "2";

    public boolean isArtificial() {
        return Objects.equals(getCode(), ARTIFICIAL_SCHOOL);
    }

    public boolean isFree() {
        return Objects.equals(getCode(), FREE_SCHOOL);
    }
}
