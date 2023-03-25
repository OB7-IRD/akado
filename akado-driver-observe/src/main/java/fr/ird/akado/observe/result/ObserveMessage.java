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
import fr.ird.akado.core.common.AkadoMessage;
import fr.ird.akado.observe.Constant;
import fr.ird.common.message.Message;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveMessage extends AkadoMessage {

    Message message;

    public ObserveMessage(String messageCode, String messageLabel, ArrayList list, String messageType) {
        message = new Message(messageCode, messageLabel, list, messageType);
    }

    public ObserveMessage(String messageCode, String messageLabel, Object param, String messageType) {
        ArrayList list = new ArrayList();
        list.add(param);
        message = new Message(messageCode, messageLabel, list, messageType);
    }

    @Override
    public String getContent() {
        return message.displayMessage(Constant.AKADO_OBSERVE_BUNDLE_PROPERTIES,
                                      Locale.forLanguageTag(AAProperties.L10N));//System.getProperty("user.language")));

    }

}
