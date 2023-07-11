/*
 * Copyright (C) 2015 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.observe.result;

import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AbstractResult;
import fr.ird.akado.core.common.MessageDescription;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Définit la classe de résultat d'une analyse d'Akado pour ObServe.
 * <p>
 * Created on 25/03/2023.
 *
 * @param <T> le type de la donnée analysée
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class Result<T> extends AbstractResult<T> {

    public Result(T datum, MessageDescription messageDescription) {
        set(Objects.requireNonNull(datum));
        setMessageCode(messageDescription.getMessageCode());
        setMessageLabel(messageDescription.getMessageLabel());
        setMessageType(messageDescription.getMessageType());
        setInconsistent(messageDescription.isInconsistent());
    }

    @Override
    protected ObserveMessage createMessage(String messageCode, String messageLabel, List<?> list, String messageType) {
        return new ObserveMessage(messageCode, messageLabel, list, messageType);
    }

    public static String translate(String i18nKey) {
        Locale locale = Locale.forLanguageTag(AAProperties.L10N);
        ResourceBundle messages
                = ResourceBundle.getBundle(ObserveMessage.AKADO_OBSERVE_BUNDLE_PROPERTIES, locale);
        return messages.getString(i18nKey);
    }
}
