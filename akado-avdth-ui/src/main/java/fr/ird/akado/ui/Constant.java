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
package fr.ird.akado.ui;

import java.util.Locale;
import org.joda.time.DateTime;

/**
 * Set of constant.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 */
public interface Constant {

    /**
     * The default locale of the application.
     */
    public final Locale DEFAULT_LOCALE = Locale.UK;
    /**
     * The name of the application.
     */
    public final static String APPLICATION_NAME = "AKaDo";
    /**
     * The author of the application.
     */
    public final static String APPLICATION_AUTHOR = "Julien Lebranchu / IRD / Ob7";
    /**
     * The year of application creation.
     */
    public final static String APPLICATION_YEAR = "2015 - " + DateTime.now().getYear();
    /**
     * The version number of the application.
     */
    public final static String APPLICATION_VERSION = "2.0.6";
    /**
     * Path of the splash screen image.
     */
    public static final String SPLASH = "/fr/ird/akado/ui/swing/resources/bg_akado.png";
    /**
     * Path of the application icon.
     */
    public static final String APPLICATION_ICON = "/fr/ird/akado/ui/swing/resources/logo_akado.png";

}
