package finance;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class DateUtils {

    public static Date getLastMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getThisMonthFirstDay());
        calendar.add(MONTH, -1);
        return calendar.getTime();
    }

    public static Date getThisMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(DAY_OF_MONTH, 1);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        calendar.set(SECOND, 0);
        calendar.set(MILLISECOND, 0);
        return calendar.getTime();
    }
}
