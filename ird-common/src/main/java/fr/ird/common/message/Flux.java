/*
 * Copyright (C) 2013 Observatoire thonier, IRD
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A Flux representing a wrapper of messages. The purpose of this class is
 * provide feedback.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.1
 * @version 1.1
 * @date 25 nov. 2013
 *
 */
public class Flux {

    private ArrayList<Message> messages;
    private String identifier;
    public static final Locale DEFAULT_LOCALE = new Locale("fr", "FR");
    private Locale currentLocale = Flux.DEFAULT_LOCALE;

    /**
     * Initializes a newly created Flux object so that it represents an empty
     * stream of messages.
     */
    public Flux() {
        messages = new ArrayList<Message>();
    }

    /**
     * Appends the specified message to the end of this stream.
     *
     * @param m the message to add
     * @return true if the message has been added
     */
    public boolean addMessage(Message m) {
        return messages.add(m);
    }

    /**
     * Returns all messages .
     *
     * @return the list which contains all messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the identifier of the current object.
     *
     * @return the identifier of this stream.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier of the stream. The identifier distinguishes two
     * streams.
     *
     * @param identifier an identifier, the new identifier for this stream
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Check if the flux has a warning message.
     *
     * @return true if the flux has a warning message
     */
    public boolean hasWarningMessage() {
        for (Message m : messages) {
            if (m.getType().equals(Message.WARNING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the message list which have the property "warning".
     *
     * @return the list of warning messages
     */
    public List<Message> getWarningMessages() {
        ArrayList<Message> tmp = new ArrayList<Message>();
        for (Message m : messages) {
            if (m.getType().equals(Message.WARNING)) {
                tmp.add(m);
            }
        }
        return tmp;
    }

    /**
     * Check if the flux has an error message.
     *
     * @return true if the flux has an error message
     */
    public boolean hasErrorMessage() {
        for (Message m : messages) {
            if (m.getType().equals(Message.ERROR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the message list which have the property "error".
     *
     * @return the list of error messages
     */
    public List<Message> getErrorMessages() {
        ArrayList<Message> tmp = new ArrayList<Message>();
        for (Message m : messages) {
            if (m.getType().equals(Message.ERROR)) {
                tmp.add(m);
            }
        }
        return tmp;
    }

    /**
     * Check if the flux has an info message.
     *
     * @return true if the flux has an info message
     */
    public boolean hasInfoMessage() {
        for (Message m : messages) {
            if (m.getType().equals(Message.INFO)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the message list which have the property "info".
     *
     * @return the list of info messages
     */
    public List<Message> getInfoMessages() {
        ArrayList<Message> tmp = new ArrayList<Message>();
        for (Message m : messages) {
            if (m.getType().equals(Message.INFO)) {
                tmp.add(m);
            }
        }
        return tmp;
    }

    /**
     * Create a list of messages in the specified language.
     *
     * @param messages the messages to translate
     * @param locale the language in which translate
     * @return the message list
     */
    public static List<String> generateMessage(List<Message> messages, Locale locale) {
        ArrayList<String> tmp = new ArrayList<String>();
        for (Message m : messages) {
            tmp.add(m.displayMessage(locale));
        }
        return tmp;
    }

    /**
     * The CurrentLocale is used to the internationalisation of Flux. A Locale
     * object represents a specific geographical, political, or cultural region.
     * The default value is en_EN.
     *
     * @param currentLocale Set a new locale to Flux
     */
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;

    }

    /**
     * The CurrentLocale is used to the internationalisation of Flux. A Locale
     * object represents a specific geographical, political, or cultural region.
     * The default value is en_EN.
     *
     * @see java.util.Locale
     * @return the current locale
     */
    public Locale getCurrentLocale() {
        if (this.currentLocale == null) {
            return DEFAULT_LOCALE;
        }
        return currentLocale;
    }

}
