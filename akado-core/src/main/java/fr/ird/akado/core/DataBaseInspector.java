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
package fr.ird.akado.core;

import fr.ird.akado.core.common.AbstractResult;
import fr.ird.akado.core.common.AbstractResults;
import fr.ird.akado.core.common.AkadoMessages;
import fr.ird.akado.core.common.ResultAdapter;
import fr.ird.akado.core.selector.TemporalSelector;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.joda.time.DateTime;

/**
 * Classe représentant le point d'entrée d'une analyse sur une base de donnée.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 */
public abstract class DataBaseInspector {

//    protected String bundleProperties;
    private AbstractResults results;
    private AkadoMessages akadoMessages;

    public AkadoMessages getAkadoMessages() {
        if (akadoMessages == null) {
            setAkadoMessages(new AkadoMessages());
        }
        return akadoMessages;
    }

    public void setAkadoMessages(AkadoMessages am) {
        this.akadoMessages = am;
        getResults().addResultListener(new ResultAdapter() {
            @Override
            public void resultAdded(AbstractResult result) {
                akadoMessages.add(result.getMessage());
            }
        });
    }

    public void setResults(AbstractResults results) {
        this.results = results;
    }

    public AbstractResults getResults() {
        return results;
    }

    private String url;
    private String driver;
    private String login;
    private String password;

    /**
     *
     * @param url the URL of the database to connect to
     * @param driver the driver class of the database
     * @param login the login of the database to connect to
     * @param password the password of the database to connect to
     */
    public DataBaseInspector(String url, String driver, String login, String password) {
        inspectors = new ArrayList<>();
        this.url = url;
        this.driver = driver;
        this.login = login;
        this.password = password;
    }

    public DataBaseInspector() {
        inspectors = new ArrayList<>();
    }

    protected List<Inspector<?>> inspectors;

    public List<Inspector<?>> getInspectors() {
        // Automatically generated method. Please do not modify this code.
        return this.inspectors;
    }

    public void setInspectors(List<Inspector<?>> value) {
        // Automatically generated method. Please do not modify this code.
        this.inspectors = value;
    }

    public abstract void validate() throws Exception;

    public abstract void info() throws Exception;

    public static Properties CONFIGURATION_PROPERTIES;

    public abstract void close();

    public void setTemporalConstraint(DateTime start, DateTime end) {
        temporalSelector = new TemporalSelector(start, end);
    }
    protected static TemporalSelector temporalSelector;
}
