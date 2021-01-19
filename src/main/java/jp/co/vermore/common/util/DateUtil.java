package jp.co.vermore.common.util;

import jp.co.vermore.form.admin.ShopRegistForm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * DateUtil
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/01 12:18
 * Copyright: sLab, Corp
 */

public class DateUtil extends org.apache.commons.lang.time.DateUtils {

    public final static long ONE_DAY_SECONDS = 86400;

    public final static String yyyyMMdd = "yyyyMMdd";

    public final static String yyyy_MM_dd = "yyyy-MM-dd";

    public final static String YYYY年MM月DD日 = "yyyy年MM月dd日";

    public final static String YYYYMM = "yyyyMM";

    public final static String yyyy_MM_ddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public final static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    public final static String yyyy_MM_ddHHmm = "yyyyMMddHHmm";

    public final static String HHmmss = "HHmmss";

    public final static String yyy_MM_ddHHmm = "yyyy-MM-dd HH:mm";

    public final static String yyyy_MM_ddHH = "yyyy-MM-dd HH";

    public static Date getNowDate() {
        return new Date();
    }

    public static Date formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        ParsePosition pos = new ParsePosition(0);
        Date newDate = formatter.parse(dateString, pos);
        return newDate;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyyMMddS(Date date) {
        String format = YYYY年MM月DD日;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyyMM(Date date) {
        String format = YYYYMM;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyy_MM_dd(Date date) {
        String format = yyyy_MM_dd;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyy_MM_dd_hhmmss(Date date) {
        String format = yyyy_MM_ddHHmmss;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyy_MM_ddHHmm(Date date) {
        String format = "yyyy.MM.dd HH:mm";
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String getYyyyMMdd(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(new Date());
        return today;
    }

    public static String getMonthStartYyyyMMdd(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String firstday ;
        Calendar cale = Calendar.getInstance();
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = df.format(cale.getTime());
        return firstday;
    }

    public static String getLastmonthStartYyyyMMdd(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String firstday ;
        Calendar cale = Calendar.getInstance();
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = df.format(cale.getTime());
        return firstday;
    }

    public static String getTodayYyyy_MM_dd_HH_mm_ss(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String today = df.format(new Date());
        return today+" "+"00:00:00";
    }

    public static String getYyyyMMddMin(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String today = df.format(new Date());
        return today;
    }

    public static String getNow(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String now = df.format(new Date());
        return now+":00";
    }

    public static String dateToStringYyyy_MM_dd_hhmm(Date date) {
        String format = yyy_MM_ddHHmm;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String getTomorrow(){
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);
        date=calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = df.format(date);
        return tomorrow;
    }

    public static String getTomorrowMin(){
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);
        date=calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String tomorrow = df.format(date);
        return tomorrow;
    }

    public static String getYesterday(){
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);
        date=calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = df.format(date);
        return yesterday;
    }

    public static String getYesterdayYyyy_MM_dd_HH_mm_ss(){
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-1);
        date=calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = df.format(date) +" "+"00:00:00";
        return yesterday;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        date = calendar.getTime();
        return date;
    }

    public static String getTomorrowYyyy_MM_dd_HH_mm_ss(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter +" "+"00:00:00";
    }

    public static String dateToStringyyyy_MM_dd_HH_mm(Date date) {
        String format = yyy_MM_ddHHmm;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyyMMddForSerialNumber(Date date) {
        String format = yyyyMMdd;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyyMMdd(Date date) {
        String format = yyyy_MM_dd;
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String dateToStringyyyymmdd(Date date) {
        String format = "yyyy.MM.dd";
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date stringToDateyyyy_MM_dd(String format) {
        if (format == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd);
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDateyyyy_MM_dd_HH_mm_ss(String format) {
        if (format == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_ddHHmmss);
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDateyyyy_MM_dd_HH_mm(String format) {
        if (format == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(yyy_MM_ddHHmm);
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // minutes ago or after
    public static String getTimeByMinuteYyyy_MM_ddHHmm(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return new SimpleDateFormat(yyy_MM_ddHHmm).format(calendar.getTime());

    }

    //minutes ago or after
    public static String getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return new SimpleDateFormat(yyyy_MM_ddHHmmss).format(calendar.getTime());

    }

    //hours ago or after
    public static String getTimeByHour(int hour) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

        return new SimpleDateFormat(yyy_MM_ddHHmm).format(calendar.getTime());

    }

    //lastMonth year
    public static String getLastDate(String currentDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(currentDate + "-" + "01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);

        String lastDate = c.get(Calendar.YEAR) + "-"
                + (c.get(Calendar.MONTH) + 1);
        return lastDate;
    }

    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static String timeDifference(Date date) {
        String str = "";
        Date systemTime = new Date(System.currentTimeMillis());
        long to = systemTime.getTime();
        long from = date.getTime();
        long time = to - from;
        long days = time / (1000 * 60 * 60 * 24);
        long hours = time / (1000 * 60 * 60);
        if (days >= 1) {
            int t = (int) (time / (1000 * 60 * 60 * 24));
            str = t + "日前";
        } else if (hours < 1) {
            int t = (int) (time / (1000 * 60));
            str = t + "分前";
        } else if (hours > 1 || hours == 1) {
            int t = (int) (time / (1000 * 60 * 60));
            str = t + "時間前";
        }

        return str;
    }

    //Dec 12,2018
    public static String stringToDateMM_dd_yyyy(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[1] + " " + k[2] + "," + k[5];
    }

//    public static Date stringToDateyyyy_MM_dd_HH_mm_ss(String str) {
//        SimpleDateFormat formatter = new SimpleDateFormat(yyyy_MM_ddHHmmss);
//        Date result = null;
//        try {
//            result = formatter.parse(str);
//        } catch (ParseException e) {}
//        return result;
//    }

    public static String unixTimeStringTime(int time) {
        String hourTime = "";
        String minuTime = "";
        String stringTime = String.valueOf(time);
        if (stringTime.length() == 4) {
            hourTime = stringTime.substring(0, 2);
            minuTime = stringTime.substring(2, 4);
        } else if (stringTime.length() == 3) {
            hourTime = stringTime.substring(0, 1);
            minuTime = stringTime.substring(1, 3);
        }
        String timeString = hourTime + ":" + minuTime;
        return timeString;
    }

    public static int stringToUnixTime(String time) {
        return Integer.parseInt(time.replace(":", ""));
    }


    public static long dateToUnixTime(Date time) {
        return time.getTime();
    }

    public static long getNowUnixTime() {
        return (new Date()).getTime();
    }


    public static String getWeekOfDate(Date date) {
        if (date == null) {
            return null;
        }
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static int getWeek(ShopRegistForm form) {
        int weekDays = 10000000;
        for (int week : form.getWeekDayList()) {
            if (week == 1) {
                weekDays = weekDays + 1000000;
            } else if (week == 2) {
                weekDays = weekDays + 100000;
            } else if (week == 3) {
                weekDays = weekDays + 10000;
            } else if (week == 4) {
                weekDays = weekDays + 1000;
            } else if (week == 5) {
                weekDays = weekDays + 100;
            } else if (week == 6) {
                weekDays = weekDays + 10;
            } else if (week == 7) {
                weekDays = weekDays + 1;
            } else {
                weekDays = weekDays + 0;
            }
        }
        return weekDays;
    }

    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static Date getDefaultDate() {
        return new Date(1 * 1000);
    }

    public static Date getDefaultPublishEnd() {
        return stringToDateyyyy_MM_dd_HH_mm_ss("2038-01-01 00:00:01");
    }

    public static List<Integer> getWeek(int weekdays) {
        List<Integer> weekList = new ArrayList<>();
        String weekStr = String.valueOf(weekdays);

        for (int i = 1; i < weekStr.length(); i++) {
            if (weekStr.charAt(i) != '0') {
                weekList.add(i);
            } else {
                weekList.add(0);
            }
        }
        return weekList;
    }

    public static Date getBrithdayDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static boolean judgeDate(Date date, Date dateFrom, Date dateTo) {

        String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String dateFromStr = dateToStringyyyy_MM_dd(dateFrom);
        String dateToStr = dateToStringyyyy_MM_dd(dateTo);

        if (dateFrom != null && dateTo != null) {
            if ((date.getTime() >= dateFrom.getTime() || dateStr.equals(dateFromStr)) && (dateStr.equals(dateToStr) || date.getTime() <= dateTo.getTime())) {
                return false;
            } else {
                return true;
            }
        } else if (dateFrom != null && dateTo == null) {
            if (date.getTime() >= dateFrom.getTime()) {
                return false;
            } else {
                return true;
            }
        } else if (dateFrom == null && dateTo != null) {
            if (date.getTime() <= dateTo.getTime()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static Integer calAge(Date birthday) {
        long bir = birthday.getTime();
        long now = System.currentTimeMillis();
        long xx = (now - bir) / 1000 / 60 / 60 / 24 / 365;
        return Integer.valueOf((int) xx);
    }

    public static boolean judgeAge(int age, int from, int to) {
        if (from != 0 && to != 0) {
            if (age >= from && age <= to) {
                return false;
            } else {
                return true;
            }
        } else if (from != 0 && to == 0) {
            if (age >= from) {
                return false;
            } else {
                return true;
            }
        } else if (from == 0 && to != 0) {
            if (age <= to) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
