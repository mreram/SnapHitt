package mreram.feediranig;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by m.eram on 8/10/2017.
 */

public class TimeAgo {
    private static String monthAgo;
    private static String weekAgo;
    private static String daysAgo;
    private static String hoursAgo;
    private static String minAgo;
    private static String secAgo;
    static int second = 1000; // milliseconds
    static int minute = 60;
    static int hour = minute * 60;
    static int day = hour * 24;
    static int week = day * 7;
    static int month = day * 30;
    static int year = month * 12;

    @SuppressLint("SimpleDateFormat")
    public static String DateDifference(long fromDate) {

        monthAgo = AppLoader.getContext().getString(R.string.mounth_ago);
        weekAgo = AppLoader.getContext().getString(R.string.week_ago);
        daysAgo = AppLoader.getContext().getString(R.string.day_ago);
        hoursAgo = AppLoader.getContext().getString(R.string.hour_ago);
        minAgo = AppLoader.getContext().getString(R.string.min_ago);
        secAgo = AppLoader.getContext().getString(R.string.sec_ago);

        long diff = 0;
        long ms2 = System.currentTimeMillis();
        // get difference in milli seconds
        diff = ms2 - fromDate;

        int diffInSec = Math.abs((int) (diff / (second)));
        String difference = "";
        if (diffInSec < minute) {
            difference = diffInSec + secAgo;
        } else if ((diffInSec / hour) < 1) {
            difference = (diffInSec / minute) + minAgo;
        } else if ((diffInSec / day) < 1) {
            difference = (diffInSec / hour) + hoursAgo;
        } else if ((diffInSec / week) < 1) {
            difference = (diffInSec / day) + daysAgo;
        } else if ((diffInSec / month) < 1) {
            difference = (diffInSec / week) + weekAgo;
        } else if ((diffInSec / year) < 1) {
            difference = (diffInSec / month) + monthAgo;
        } else {
            // return date
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fromDate);

            SimpleDateFormat format_before = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");

            difference = format_before.format(c.getTime());
        }
        if (diffInSec == 0) {
            difference = AppLoader.getContext().getString(R.string.now);
        }


        return difference;
    }

    @SuppressLint("SimpleDateFormat")
    public static String DateDifference(String strDate) {
        monthAgo = AppLoader.getContext().getString(R.string.mounth_ago);
        weekAgo = AppLoader.getContext().getString(R.string.week_ago);
        daysAgo = AppLoader.getContext().getString(R.string.day_ago);
        hoursAgo = AppLoader.getContext().getString(R.string.hour_ago);
        minAgo = AppLoader.getContext().getString(R.string.min_ago);
        secAgo = AppLoader.getContext().getString(R.string.sec_ago);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'KK:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return strDate;
        }
        long fromDate = date != null ? date.getTime() : 0;
        long diff = 0;
        long ms2 = System.currentTimeMillis();
        // get difference in milli seconds
        diff = ms2 - fromDate;

        int diffInSec = Math.abs((int) (diff / (second)));

        String difference = "";
        if (diffInSec < minute) {
            difference = diffInSec + secAgo;
        } else if ((diffInSec / hour) < 1) {
            difference = (diffInSec / minute) + minAgo;
        } else if ((diffInSec / day) < 1) {
            difference = (diffInSec / hour) + hoursAgo;
        } else if ((diffInSec / week) < 1) {
            difference = (diffInSec / day) + daysAgo;
        } else if ((diffInSec / month) < 1) {
            difference = (diffInSec / week) + weekAgo;
        } else if ((diffInSec / year) < 1) {
            difference = (diffInSec / month) + monthAgo;
        } else {
            // return date
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(fromDate);

            SimpleDateFormat format_before = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            difference = SolarCalendar.getShamsidateWithLabel(strDate);
//            difference = format_before.format(c.getTime());
        }
        if (diffInSec == 0) {
            difference = AppLoader.getContext().getString(R.string.now);
        }


        return difference;
    }


}