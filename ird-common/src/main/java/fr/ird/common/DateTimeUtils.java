/*
 * Copyright (C) 2015 Observatoire thonier, IRD
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
package fr.ird.common;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Miscellaneous methods to manipulate the date and the time object.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class DateTimeUtils {

    /**
     * Compares the equality of two dates, It checks the year, month and day.
     *
     * @param current the current date
     * @param reference
     * @return true or false
     */
    public static boolean dateEqual(Date current, Date reference) {
        if (current == null || reference == null) {
            return false;
        }
        DateTime dtCourante = new DateTime(current);

        DateTime dtReference = new DateTime(reference);

        return dateEqual(dtCourante, dtReference);
    }
    public static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy").withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));
    public static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy HH:MM").withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));

    /**
     * Compares the equality of two dates, It checks the year, month and day.
     *
     * @param current the current date
     * @param reference
     * @return true or false
     */
    public static boolean dateEqual(DateTime current, DateTime reference) {
        if (current == null || reference == null) {
            return false;
        }

        return current.getYear() == reference.getYear()
                && current.getMonthOfYear() == reference.getMonthOfYear()
                && current.getDayOfMonth() == reference.getDayOfMonth();
    }

    /**
     * Compares if the two date are contigous. It checks the year, month and
     * day.
     *
     * @param current the current date
     * @param next the next day
     * @return true or false
     */
    public static boolean dateIsTheNextDay(Date current, Date next) {
        if (current == null || next == null) {
            return false;
        }
        DateTime cDay = new DateTime(current);

        DateTime nDay = new DateTime(next);

        return dateIsTheNextDay(cDay, nDay);
    }

    /**
     * Compares if the two date are contigous. It checks the year, month and
     * day.
     *
     * @param current the current date
     * @param next
     * @return true or false
     */
    public static boolean dateIsTheNextDay(DateTime current, DateTime next) {
        if (current == null || next == null) {
            return false;
        }

        return dateEqual(current, next.minusDays(1));

    }

    /**
     * Compares the date with the specified dateRef for next based on the
     * millisecond instant, chronology and time zone.
     *
     * @param date
     * @param referenceDate
     * @return true of false
     */
    public static boolean dateAfter(Date date, Date referenceDate) {
        if (date == null || referenceDate == null) {
            return false;
        }
        DateTime dateCourante = new DateTime(date);

        DateTime dateReference = new DateTime(referenceDate);

        return dateAfter(dateCourante, dateReference);
    }

    /**
     * Compares the date with the specified dateRef for next based on the
     * millisecond instant, chronology and time zone.
     *
     * @param date
     * @param referenceDate
     * @return true of false
     */
    public static boolean dateAfter(DateTime date, DateTime referenceDate) {
        if (date == null || referenceDate == null) {
            return false;
        }

        return date.compareTo(referenceDate) > 0;
    }

    /**
     * This method calculates the difference between a date object and a
     * constant time expressed with an integer.
     *
     * @param date
     * @param hoursInMinutes the number of minutes in the hour
     * @return difference between hours and constant time
     */
    public static int differenceHoursWithConstantTime(Date date, int hoursInMinutes) {
        final DateTime dt = new DateTime(date.getTime());
        return differenceHoursWithConstantTime(dt, hoursInMinutes);
    }

    /**
     * This method calculates the difference between a date object and a
     * constant time expressed with an integer.
     *
     * @param date
     * @param hoursInMinutes the number of minutes in the hour
     * @return difference between hours and constant time
     */
    public static int differenceHoursWithConstantTime(DateTime date, int hoursInMinutes) {
        int dateInMinutes = date.getHourOfDay() * 60 + date.getMinuteOfHour();
        float res = (hoursInMinutes - dateInMinutes) / 60f;
        if (dateInMinutes > hoursInMinutes) {
            res = (dateInMinutes - hoursInMinutes) / 60f;
        }
        return Math.round(res);
    }

    /**
     * Calculates the number of whole days between the two specified datetimes.
     * This method corectly handles any daylight savings time changes that may
     * occur during the interval.
     *
     * @param start the start instant, must not be null
     * @param end the end instant, must not be null
     * @return the number of days between two dates
     */
    public static int daysBetween(Date start, Date end) {
        return daysBetween(new DateTime(start.getTime()), new DateTime(end.getTime()));
    }

    /**
     * Calculates the number of whole days between the two specified datetimes.
     * This method corectly handles any daylight savings time changes that may
     * occur during the interval.
     *
     * @param start the start instant, must not be null
     * @param end the end instant, must not be null
     * @return the number of days between two dates
     */
    public static int daysBetween(DateTime start, DateTime end) {
        return Days.daysBetween(start, end).getDays();
    }

    /**
     * Calculates the number of whole hours between the two specified datetimes.
     * This method corectly handles any daylight savings time changes that may
     * occur during the interval.
     *
     * @param start the start instant, must not be null
     * @param end the end instant, must not be null
     * @return the number of hours between two dates
     */
    public static int hoursBetween(Date start, Date end) {
        DateTime startTime = new DateTime(start.getTime());
        DateTime endTime = new DateTime(end.getTime());
        return hoursBetween(startTime, endTime);
    }

    /**
     * Calculates the number of whole hours between the two specified datetimes.
     * This method corectly handles any daylight savings time changes that may
     * occur during the interval.
     *
     * @param start the start instant, must not be null
     * @param end the end instant, must not be null
     * @return the number of hours between two dates
     */
    public static int hoursBetween(DateTime start, DateTime end) {
        Period p = new Period(start, end);
//        System.out.println(PeriodFormat.getDefault().print(p));
        return p.getHours();
    }

    /**
     * Gives the number of hours associated with a date time.
     *
     * @param date
     * @return
     */
    public static int getHours(Date date) {
        final DateTime d = new DateTime(date.getTime());
        return d.getHourOfDay();
    }

    /**
     * Gives the number of hours associated with a date time.
     *
     * @param date
     * @return
     */
    public static int getHours(DateTime date) {
        return date.getHourOfDay();
    }

    /**
     * Gives the number of minutes associated with a date time.
     *
     * @param date
     * @return
     */
    public static int getMinutes(Date date) {
        final DateTime d = new DateTime(date.getTime());
        return d.getMinuteOfHour();
    }

    /**
     * Gives the number of minutes associated with a date time.
     *
     * @param date the date
     * @return the number of minutes
     */
    public static int getMinutes(DateTime date) {
        return date.getMinuteOfHour();
    }

    /**
     *
     * @param time the time
     * @return the time converted
     */
    public static LocalTime convertTime(Time time) {
        return time == null ? null : new LocalTime(time.getTime());
    }

    /**
     *
     * @param time the time
     * @return the time converted
     */
    public static Time convertTime(LocalTime time) {
        return time == null ? null : new Time(time.getMillisOfDay());
    }

    /**
     * Convert {@link java.sql.Date} to {@link DateTime}.
     *
     * @param date the date
     * @return the date converted
     */
    public static DateTime convertDate(Date date) {
        return date == null ? null : new DateTime(date);
    }

    /**
     * Convert {@link DateTime} to {@link java.sql.Date}.
     *
     * @param date the date
     * @return the date converted
     */
    public static java.sql.Date convertDate(DateTime date) {
        return date == null ? null : new java.sql.Date(date.getMillis());//convertFullDate(new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0));
//        return convertFullDate(date);
    }

    /**
     * Convert {@link DateTime} to {@link java.sql.Date} filtering the hour and
     * minute values.
     *
     * @param date the date
     * @return the date converted
     */
    public static java.sql.Date convertFilteredDate(DateTime date) {
        return date == null ? null : convertFullDate(new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0));

    }

    /**
     * Convert {@link DateTime} to {@link java.sql.Date} with all fields (year,
     * month, day, hour and minute).
     *
     * @param date the date
     * @return the date converted
     */
    public static java.sql.Date convertFullDate(DateTime date) {
        return date == null ? null : new java.sql.Date(date.getMillis());
    }

    /**
     * Calcul le nombre d'heures entre deux dates.
     *
     * @param start
     * @param end
     * @return
     */
    public static int differenceHours(Date start, Date end) {
        final DateTime debut = new DateTime(start.getTime());
        final DateTime fin = new DateTime(end.getTime());
        return Hours.hoursBetween(debut, fin).getHours();
    }

    /**
     * Calcul le nombre de jours entre deux dates.
     *
     * @param start
     * @param end
     * @return
     */
    public static int differenceDays(Date start, Date end) {
        final DateTime debut = new DateTime(start.getTime());
        final DateTime fin = new DateTime(end.getTime());
        return Days.daysBetween(debut, fin).getDays();
    }

    public static final Pattern pDateTime = Pattern.compile("^(?<date>[0-9]{4}-[0-9]{2}-[0-9]{2}) (?<time>[0-9]{2}:[0-9]{2}:.*)$");
    public static final Pattern pTime = Pattern.compile("^(?<time>[0-9]{2}:[0-9]{2}:.*)$");

    /**
     * Add the time to the date.
     *
     * @param date
     * @param field
     * @return a new date object with the time field filled
     */
    public static DateTime addTimeTo(DateTime date, String field) {
        String time;
        Matcher matcher = pDateTime.matcher(field);
        if (matcher.matches()) {
            time = matcher.group("time");
        } else {
            matcher = pTime.matcher(field);
            if (matcher.matches()) {
                time = matcher.group("time");
            } else {
                return null;
            }
        }

        String[] timesArray = time.split(":");
        int hour = Integer.parseInt(timesArray[0]);
        int minute = Integer.parseInt(timesArray[1]);
        int second = 0;
        if (timesArray.length > 2) {
            second = Math.round(Float.parseFloat(timesArray[2]));

        }
        final DateTimeZone dtz = DateTimeZone.forID("UTC");
        LocalDateTime ldt = new LocalDateTime(dtz)
                .withYear(date.getYear())
                .withMonthOfYear(date.getMonthOfYear())
                .withDayOfMonth(date.getDayOfMonth())
                .withHourOfDay(hour)
                .withMinuteOfHour(minute)
                .withSecondOfMinute(second);

        return ldt.toDateTime(dtz);
//        return new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), hour, minute, second);
    }

    /**
     * Converts the time in hours in millis.
     *
     * @param hour hour to convert
     * @return the millis
     */
    public static long convertHoursInMillis(int hour) {
        return convertMinutesInMillis(hour * 60);
    }

    /**
     * Converts the time in minutes in millis.
     *
     * @param minute minute to convert
     * @return the millis
     */
    public static long convertMinutesInMillis(int minute) {
        return convertSecondsInMillis(minute * 60);
    }

    /**
     * Converts the time in seconds in millis.
     *
     * @param second second to convert
     * @return the millis
     */
    public static long convertSecondsInMillis(int second) {
        return second * 1000;
    }

    public static DateTime convertLocalDate(LocalDate ld) {
        if (ld == null) {
            return null;
        }
        return new DateTime(ld.getYear(), ld.getMonthValue(), ld.getDayOfMonth(), 0, 0);
    }

}
