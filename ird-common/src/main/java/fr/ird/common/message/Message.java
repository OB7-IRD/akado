/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.common.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Représente un message qui sera manipulé dont le contenu se trouve dans les
 * fichiers d'internationalisation.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 25 nov. 2013
 * @since 1.1
 */
public class Message {

    public static final String WARNING = "warning";
    public static final String ERROR = "error";
    public static final String INFO = "info";
    public static final String SUCCESS = "success";

    protected String code;
    protected String label;
    protected String type;
    protected ArrayList<Object> params;

    /**
     * Initializes a newly created Message object so that it represents an
     * message with a specific code and many parameters and a type of message
     * (ERROR, WARNING, INFO, SUCCESS).
     *
     * @param code the code of the message
     * @param label the key of the locale properties
     * @param params parameters to be applied to the message
     * @param type the type of the message
     */
    public Message(String code, String label, ArrayList<Object> params, String type) {
        this.code = code;
        this.label = label;
        this.params = params;
        this.type = type;
    }

    /**
     * Initializes a newly created Message object so that it represents an
     * message with a specific code and a type of message (ERROR, WARNING, INFO,
     * SUCCESS).
     *
     * @param code the code of the message
     * @param label the key of the locale properties
     * @param type the type of the message
     */
    public Message(String code, String label, String type) {
        this(code, label, new ArrayList(), type);
    }

    /**
     * Initializes a newly created Message object so that it represents an
     * message with a specific code and many parameters.
     *
     * @param code the code of the message
     * @param label the key of the locale properties
     * @param params parameters to be applied to the message
     */
    public Message(String code, String label, ArrayList<Object> params) {
        this(code, label, params, INFO);
    }

    /**
     * Initializes a newly created Message object so that it represents an
     * message with a specific code.
     *
     * @param code the code of the message
     * @param label the key of the locale properties
     */
    public Message(String code, String label) {
        this(code, label, new ArrayList(), INFO);
    }

    /**
     * The code enable to specify the content of the current message.
     *
     * @return the code of the current message
     */
    public String getCode() {
        return code;
    }

    /**
     * The params contain the values which have generated the current message.
     *
     * @return the list of message parameters
     */
    public ArrayList<Object> getParams() {
        return params;
    }

    /**
     * The type of message can be ERROR, WARNING, INFO and SUCCESS.
     *
     * @return the type of message
     */
    public String getType() {
        return type;
    }

    /**
     * The label is the key for the locale properties.
     *
     * @return the label of message
     */
    public String getLabel() {
        return label;
    }

    /**
     * Displays the message with the parameters in the selected language.
     *
     * @param baseName the base name of the resource bundle, a fully qualified
     * class name
     * @param locale the message's language to display
     * @return the String message
     */
    public String displayMessage(String baseName, Locale locale) {
        ResourceBundle messages
                = ResourceBundle.getBundle(baseName, locale);
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);
        formatter.applyPattern(messages.getString(this.getLabel()));

        return formatter.format(this.getParams().toArray());

    }

    /**
     * Displays the message with the parameters in the selected language.
     *
     * @param locale the message's language to display
     * @return the String message
     */
    public String displayMessage(Locale locale) {
        return this.displayMessage("Message", locale);
    }

    /**
     * Displays the message with the parameters in the selected language.
     *
     * @return the String message
     */
    public String displayMessage() {
        return this.displayMessage("Message", Locale.getDefault());
    }

}
