/*
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.core.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente le résultat d'un controle. Il doit etre déclinée en fonction des
 * applications logicielles (ERS, AVDTH,...).
 *
 * @param <T> le type du résultat
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 9 juil. 2014
 * @since 2.0
 */
public abstract class AbstractResult<T> {

    protected AkadoMessage message;
    protected Object valueExpected;
    protected Object valueObtained;
    protected Object dataInformation;
    protected String messageCode;
    protected String messageType;
    protected String messageLabel;
    protected List<?> messageParameters;
    protected boolean inconsistent;
    private T t;

    public abstract <X> List<X> extractResults();

    protected abstract AkadoMessage createMessage(String messageCode, String messageLabel, List<?> list, String messageType);

    /**
     * @return the result object
     */
    public T get() {
        return t;
    }

    /**
     * @param t the result object
     */
    public void set(T t) {
        this.t = t;
    }

    public boolean isInconsistent() {
        return inconsistent;
    }

    public void setInconsistent(boolean inconsistent) {
        this.inconsistent = inconsistent;
    }

    public void setMessageParameters(List<?> messageParameters) {
        this.messageParameters = messageParameters;
    }

    public List<?> getMessageParameters() {
        if (messageParameters == null) {
            return new ArrayList<>();
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

    /**
     * Returns the message associate with the result.
     *
     * @return the message
     */
    public final AkadoMessage getMessage() {
        if (message == null) {
            List tmp = getMessageParameters();
            String type = "E";
            if (messageType != null && !"".equals(messageType)) {
                type = messageType.toUpperCase().substring(0, 1);
            }
            tmp.add(0, type + messageCode);
            message = createMessage(messageCode, messageLabel, tmp, messageType);
        }
        return message;
    }
}
