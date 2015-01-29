package org.energyos.espi.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtils
{
    private static final String[] CALENDAR_FIELD_NAME = {
        "ERA", "YEAR", "MONTH", "WEEK_OF_YEAR", "WEEK_OF_MONTH", "DAY_OF_MONTH",
        "DAY_OF_YEAR", "DAY_OF_WEEK", "DAY_OF_WEEK_IN_MONTH", "AM_PM", "HOUR",
        "HOUR_OF_DAY", "MINUTE", "SECOND", "MILLISECOND", "ZONE_OFFSET",
        "DST_OFFSET"
    };
    
    
    public static final TimeZone LH_TIME_ZONE = TimeZone.getTimeZone("America/New_York");
    
   
    
    /**
     * Returns LH local calendar 
     * 
     * @param date Date/time
     * @return Date only calendar, without the time portion
     */
    public static Calendar getDateOnlyCalendar(Date date)
    {
        Calendar cal = getCalendarInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * Returns LH local calendar 
     * 
     * @param time Date/time in millis
     * @return Date only calendar, without the time portion
     */
    public static Calendar getDateOnlyCalendar(Long time)
    {
        Calendar cal = getCalendarInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
    
    /**
     * Returns LH local date
     *  
     * @return
     */
    public static Date getCurrentDateOnly()
    {
        Calendar cal = getCalendarInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    /**
     * Returns LH local time
     * 
     * @return
     */
    public static Date getCurrentDateTime()
    {
        Calendar cal = getCalendarInstance();
        return cal.getTime();
    }

    /**
     * Returns time in the system time zone
     * 
     * @return
     */
    public static Date getCurrentSystemDateTime()
    {
        Calendar cal = getSystemCalendarInstance();
        return cal.getTime();
    }

    /**
     * Returns LH local date
     *  
     * @return
     */
    public static Date getCurrentSystemDateOnly()
    {
        Calendar cal = getSystemCalendarInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    
    
    public static Calendar getCalendarInstance(TimeZone zone)
    {
        Calendar cal = Calendar.getInstance(zone);
        return cal;
    }
    
    public static Calendar getCalendarInstance()
    {
        Calendar cal = Calendar.getInstance(LH_TIME_ZONE);
        return cal;
    }
    /**
     * Returns LH calendar for the specified date
     * 
     * @param date Date object 
     * @return
     */
    public static Calendar getCalendarInstance(Date date)
    {
        Calendar cal = getCalendarInstance();
        cal.setTime(date);
        return cal;
    }

    
    /**
     * Returns System calendar
     * 
     * @return
     */
    public static Calendar getSystemCalendarInstance()
    {
        Calendar cal = Calendar.getInstance();
        return cal;
    }

    /**
     * Return calendar in local date/time
     * 
     * @param date
     * @return
     */
    public static Calendar getSystemCalendarInstance(Date date)
    {
        Calendar cal = getSystemCalendarInstance();
        cal.setTime(date);
        return cal;
    }
    
    /**
     * 
     * @param time date/time in milliseconds
     * @return
     */
    public static Calendar getSystemDateOnlyCalendar(long time)
    {
        Calendar cal = getSystemCalendarInstance();
        cal.setTimeInMillis(time);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 
     * @param date
     * @return
     */
    public static Calendar getSystemDateOnlyCalendar(Date date)
    {
        return getSystemDateOnlyCalendar(date.getTime());
    }
    
    /**
     * Adding days to a give Date.
     * @param date
     * @param days
     * @return new Date + days
     */
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
    
    /**
     * Checks if two calendars represent the same day ignoring time.
     * 
     * @param cal1  the first calendar, not altered, not null
     * @param cal2  the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isWithinPeriod(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) <= cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) <= cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_YEAR) <= cal2.get(Calendar.DAY_OF_YEAR));
    }
    
    /**
     * Convert to local date/time without milliseconds
     * 
     * @param time  Time for conversion  
     *  
     * @return  Local date/time instance
     */
    public static Date toLocalTime(Date time)
    {
        Calendar cal = getSystemCalendarInstance(time);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
        return cal.getTime();
    }
    
    /**
     * Calculate time difference between the two calendars
     * 
     * @param cal1  Calendar 1
     * @param cal2  Calendar 2
     * @param field <code>java.util.Calendar</code> field. Supported <codejava.util.Calendar</code> fields are YEAR, MONTH, WEEK_OF_YEAR, DATE, HOUR, MINUTE, SECOND
     * @return  Time difference in the units defined by the field
     */
    public static int timeDifference(Calendar cal1, Calendar cal2, int field)
    {
        switch(field)
        {
        case Calendar.YEAR:            
            return cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        case Calendar.MONTH:
            return timeDifference(cal1, cal2, Calendar.YEAR) * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) + 1;
        case Calendar.WEEK_OF_YEAR:
            return timeDifference(cal1, cal2, Calendar.DATE) / 7;           
        case Calendar.DATE:
            return timeDifference(cal1, cal2, Calendar.HOUR) / 24;           
        case Calendar.HOUR:
            return timeDifference(cal1, cal2, Calendar.SECOND) / 3600;           
        case Calendar.MINUTE:
            return timeDifference(cal1, cal2, Calendar.SECOND) / 60;           
        case Calendar.SECOND:
            return (int)((cal2.getTimeInMillis() - cal1.getTimeInMillis()) / 1000);        
        }
        throw new IllegalArgumentException("Unsupported Calendar field: " +  CALENDAR_FIELD_NAME[field]);
    }
    
}