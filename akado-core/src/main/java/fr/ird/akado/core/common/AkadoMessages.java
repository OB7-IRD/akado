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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.event.EventListenerList;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 23 mai 2014
 */
public class AkadoMessages extends ArrayList<AkadoMessage> {

    private static final Logger log = LogManager.getLogger(AkadoMessages.class);
    
    // un seul objet pour tous les types d'écouteurs
    private final EventListenerList listeners = new EventListenerList();
    private String bundleProperties;

    public void setBundleProperties(String bundleProperties) {
        this.bundleProperties = bundleProperties;
    }

    public String getBundleProperties() {
        return bundleProperties;
    }

    public AkadoMessages() {
    }

    @Override
    public boolean addAll(Collection<? extends AkadoMessage> c) {
        boolean added = true;
        for (AkadoMessage m : c) {
            added &= this.add(m);
        }
        return added;
    }

    @Override
    public boolean add(AkadoMessage m) {
        boolean added = super.add(m);
        fireMessageAdded(m);
        return added;
    }

    protected void fireMessageAdded(AkadoMessage m) {
//        //System.out.println("> " + m.getLabel());
        for (MessageListener listener : getMessageListeners()) {
            listener.messageAdded(m);
        }
    }

    public MessageListener[] getMessageListeners() {
        return listeners.getListeners(MessageListener.class);
    }

    public void addMessageListener(MessageListener listener) {
//        //System.out.println("MessageListener added");
        listeners.add(MessageListener.class, listener);
    }

    public void removeMessageListener(MessageListener listener) {
        listeners.remove(MessageListener.class, listener);
    }

    public void saveLog(String path) {
//        String path = DataBaseInspector.CONFIGURATION_PROPERTIES.getProperty(KEY_LOGS_DIRECTORY) + File.separator + "akado_" + new DateTime() + ".log";
//        System.out.println("LOG FILE PATH " + path);
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "utf-8"));
            String out = "";
            for (AkadoMessage m : this) {
                out += m.getContent() + "\n";//displayMessage(bundleProperties, Locale.forLanguageTag(System.getProperty("user.language"))) + "\n";

            }
            writer.write(out);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }

    }
}
