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

import fr.ird.akado.core.common.AbstractResult;
import fr.ird.akado.core.common.AkadoMessage;

import java.util.ArrayList;
import java.util.List;

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

    protected Object valueExpected;
    protected Object valueObtained;
    protected Object dataInformation;
    protected String messageCode;
    protected String messageType;
    protected String messageLabel;
    protected ArrayList messageParameters = null;
    protected boolean inconsistent = false;

    public abstract <X> List<X> extractResults();

    public boolean isInconsistent() {
        return inconsistent;
    }

    public void setInconsistent(boolean inconsistent) {
        this.inconsistent = inconsistent;
    }

    public void setMessageParameters(ArrayList messageParameters) {
        this.messageParameters = messageParameters;
    }

    public ArrayList getMessageParameters() {
        if (messageParameters == null) {
            return new ArrayList();
        }
        return messageParameters;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public void setMessageLabel(String messageLabel) {
        this.messageLabel = messageLabel;
    }

    public void setValueExpected(Object valueExpected) {
        this.valueExpected = valueExpected;
    }

    public void setValueObtained(Object valueObtained) {
        this.valueObtained = valueObtained;
    }

    public Object getValueExpected() {
        return valueExpected;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public String getMessageLabel() {
        return messageLabel;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getValueObtained() {
        return valueObtained;
    }

    public Object getDataInformation() {
        return dataInformation;
    }

    public void setDataInformation(Object dataInformation) {
        this.dataInformation = dataInformation;
    }

    @Override
    public AkadoMessage getMessage() {
        if (message == null) {
            ArrayList tmp = getMessageParameters();
            String type = "E";
            if (messageType != null && !"".equals(messageType)) {
                type = messageType.toUpperCase().substring(0, 1);
            }
            tmp.add(0, type + messageCode);
            message = new ObserveMessage(messageCode, messageLabel, tmp, messageType);
        }
        return message;
    }
}
