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
package fr.ird.akado.core.common;

import java.util.Objects;

/**
 * The AkadoProperties class represents a persistent set of properties. This
 * properties are stored in the file "akado-config.xml".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 24 juin 2014
 */
public class AAProperties {

    public static String PROTOCOL_JDBC_ACCESS;

    public static final String KEY_STANDARD_DIRECTORY = "standard_directory";
    public static final String KEY_SHP_COUNTRIES_PATH = "SHP_COUNTRIES";
    public static final String KEY_SHP_HARBOUR_PATH = "SHP_HARBOUR";
    public static final String KEY_SHP_OCEAN_PATH = "SHP_IHO_OCEANS";
    public static final String KEY_SHP_EEZ_PATH = "SHP_EEZ_IRD";
    public static final String KEY_DATE_FORMAT_XLS = "date_xls";

    public static final String KEY_SAMPLE_INSPECTOR = "sample_inspector";
    public static final String KEY_WELL_INSPECTOR = "well_inspector";
    public static final String KEY_TRIP_INSPECTOR = "trip_inspector";
    public static final String KEY_POSITION_INSPECTOR = "position_inspector";
    public static final String KEY_ANAPO_INSPECTOR = "anapo_inspector";
    public static final String KEY_ANAPO_VMS_COUNTRY = "anapo_vms_country_list";
    public static final String KEY_ACTIVITY_INSPECTOR = "activity_inspector";
    public static final String KEY_WARNING_INSPECTOR = "warning_inspector";

    public static final String KEY_AKADO_INSPECTOR = "akado_inspector";

    public static final String KEY_ANAPO_DB_PATH = "anapo_db";
    public static final String KEY_RESULTS_OUTPUT = "results_output";

    public static final String ACTIVE_VALUE = "active";
    public static final String DISABLE_VALUE = "disable";

    public static String DATE_FORMAT_XLS = "dd/mm/yyyy hh:mm";
    public static String STANDARD_DIRECTORY;
    public static String SHP_COUNTRIES_PATH;
    public static String SHP_OCEAN_PATH;
    public static String SHP_HARBOUR_PATH;
    public static String SHP_EEZ_PATH;

    public static String SAMPLE_INSPECTOR;
    public static String WELL_INSPECTOR;
    public static String TRIP_INSPECTOR;
    public static String POSITION_INSPECTOR;
    public static String ACTIVITY_INSPECTOR;
    public static String WARNING_INSPECTOR;

    public static String ANAPO_INSPECTOR = DISABLE_VALUE;

    public static String ANAPO_DB_URL;
    public static String ANAPO_USER = "";
    public static String ANAPO_PASSWORD = "";

    public static final String KEY_L10N = "lang";
    public static String L10N = "fr";

    public final static String KEY_THRESHOLD_CLASS_ONE = "threshold_class_one";
    public final static String KEY_THRESHOLD_CLASS_TWO = "threshold_class_two";
    public final static String KEY_NB_PROC = "nb_proc";

    public static double THRESHOLD_CLASS_ONE = 10d;
    public static double THRESHOLD_CLASS_TWO = 20d;
    public static String ANAPO_VMS_COUNTRY = "1|41";
    public static String VESSEL_SELECTED;
    public static String RESULTS_OUTPUT = ACTIVE_VALUE;
    public static int NB_PROC = 1;
    public static String AKADO_INSPECTOR;

    public static boolean isAkadoInspectorEnabled() {
        return Objects.equals(AKADO_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isWarningInspectorEnabled() {
        return Objects.equals(WARNING_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isPositionInspectorEnabled() {
        return Objects.equals(POSITION_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isSampleInspectorEnabled() {
        return Objects.equals(SAMPLE_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isWellInspectorEnabled() {
        return Objects.equals(WELL_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isTripInspectorEnabled() {
        return Objects.equals(TRIP_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isActivityInspectorEnabled() {
        return Objects.equals(ACTIVITY_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isAnapoInspectorEnabled() {
        return Objects.equals(ANAPO_INSPECTOR, ACTIVE_VALUE);
    }

    public static boolean isResultsEnabled() {
        return Objects.equals(RESULTS_OUTPUT, ACTIVE_VALUE);
    }

}
