package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Country extends I18nReferentialEntity {

    /**
     * @return {@code true} if country is France (code 1)
     */
    public boolean isFrance() {
        return "fr.ird.referential.common.Country#1239832675583#0.9493110781716075".equals(getTopiaId());
    }

    /**
     * @return {@code true} if country is Spain (code 4)
     */
    public boolean isSpain() {
        return "fr.ird.referential.common.Country#1239832675584#0.0783072255559325".equals(getTopiaId());
    }

    private String iso2Code;
    private String iso3Code;

    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    public String getIso3Code() {
        return iso3Code;
    }

    public void setIso3Code(String iso3Code) {
        this.iso3Code = iso3Code;
    }

    public int getCodeAsInt() {
        return Integer.parseInt(getCode());
    }

}
