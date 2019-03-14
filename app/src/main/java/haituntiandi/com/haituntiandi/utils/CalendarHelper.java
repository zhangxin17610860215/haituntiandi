package haituntiandi.com.haituntiandi.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hirondelle.date4j.DateTime;

public class CalendarHelper {

    private static SimpleDateFormat yyyyMMddFormat;
    public static void setup() {
        yyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    }

    /**
     *
     * @param month
     * @param year
     * @param startDayOfWeek
     * @param sixWeeksInCalendar 是否固定显示6行还是动态显示
     * @return
     */
    public static ArrayList<DateTime> getFullWeeks(int month, int year, int startDayOfWeek, boolean sixWeeksInCalendar) {
        ArrayList<DateTime> datetimeList = new ArrayList<DateTime>();

        DateTime firstDateOfMonth = new DateTime(year, month, 1, 0, 0, 0, 0);
        DateTime lastDateOfMonth = firstDateOfMonth.plusDays(firstDateOfMonth
                .getNumDaysInMonth() - 1);
        int weekdayOfFirstDate = firstDateOfMonth.getWeekDay();

        if (weekdayOfFirstDate < startDayOfWeek) {
            weekdayOfFirstDate += 7;
        }

        while (weekdayOfFirstDate > 0) {
            DateTime dateTime = firstDateOfMonth.minusDays(weekdayOfFirstDate
                    - startDayOfWeek);
            if (!dateTime.lt(firstDateOfMonth)) {
                break;
            }

            datetimeList.add(dateTime);
            weekdayOfFirstDate--;
        }

        for (int i = 0; i < lastDateOfMonth.getDay(); i++) {
            datetimeList.add(firstDateOfMonth.plusDays(i));
        }

        int endDayOfWeek = startDayOfWeek - 1;

        if (endDayOfWeek == 0) {
            endDayOfWeek = 7;
        }

        if (lastDateOfMonth.getWeekDay() != endDayOfWeek) {
            int i = 1;
            while (true) {
                DateTime nextDay = lastDateOfMonth.plusDays(i);
                datetimeList.add(nextDay);
                i++;
                if (nextDay.getWeekDay() == endDayOfWeek) {
                    break;
                }
            }
        }

        if (sixWeeksInCalendar) {
            int size = datetimeList.size();
            int row = size / 7;
            int numOfDays = (6 - row) * 7;
            DateTime lastDateTime = datetimeList.get(size - 1);
            for (int i = 1; i <= numOfDays; i++) {
                DateTime nextDateTime = lastDateTime.plusDays(i);
                datetimeList.add(nextDateTime);
            }
        }

        return datetimeList;
    }

    public static DateTime convertDateToDateTime(Date date) {
        // Get year, javaMonth, date
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int javaMonth = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        // javaMonth start at 0. Need to plus 1 to get datetimeMonth
        return new DateTime(year, javaMonth + 1, day, 0, 0, 0, 0);
    }
}
