package fr.ird.common;

import java.util.Date;

/**
 * Created on 04/04/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class DateUtils {

    public static String formatDate(Date date) {
        return date == null ? null : String.format("%1$td/%1$tm/%1$tY", date);
    }

    public static String formatTime(Date date) {
        return date == null ? null : String.format("%1$tH:%1$tM", date);
    }

    public static String formatDateAndTime(Date date) {
        return date == null ? null : String.format("%1$td/%1$tm/%1$tY %1$tH:%1$tM", date);
    }
}
