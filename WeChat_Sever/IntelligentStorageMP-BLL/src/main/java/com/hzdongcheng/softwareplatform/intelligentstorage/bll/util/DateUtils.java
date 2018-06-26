package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;
 
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
 
 

// Referenced classes of package com.dcdzsoft.util:
//            StringUtils

public class DateUtils
{

    private DateUtils()
    {
    }
 
    /** 
     * 两时间对比, 相差是否超过3秒 
     *  
     * @param dateA 
     * @param dateB 
     * @return 
     * @throws ParseException 
     */  
    public static boolean compare(String dateA, String dateB,int second)   {  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        //boolean   sTime= Math.abs( df.parse(dateA).getTime() - df.parse(dateB).getTime()) > 3000;
        boolean secondTime=false;
		try {
			//System.out.println("   "+ ( df.parse(dateA).getTime() - df.parse(dateB).getTime()));
			secondTime = ( df.parse(dateA).getTime() - df.parse(dateB).getTime()) > second;
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return secondTime;  
    }  
    
    
    /** 
     * 两时间对比, 相差是否超过3秒 
     *  
     * @param dateA 
     * @param dateB 
     * @return 
     * @throws ParseException 
     */  
    public static boolean compare(java.util.Date endDate, java.util.Date startDate )   {  
        boolean secondTime=false;
		try {
			secondTime = ( endDate.getTime() - startDate.getTime()) > 0;
		} catch ( Exception e) {
			e.printStackTrace();
		}
        return secondTime;  
    } 
    
    
    public static void main(String args[])  throws Exception   {
 
    	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
         	java.util.Date storeintime=df.parse("2017-11-25 00:00:01");  
         	java.util.Date nowDate= new java.util.Date();
         	Integer boxtypecode= 0;
			Integer chargeModeInt=0;
		    int moneyTotal= 0;
 
		    java.util.Date   storeintimeTemp=storeintime;
		    do{
		    	java.util.Date storeintimeTempUp=storeintimeTemp;
		    	storeintimeTemp= (java.util.Date) addDate(storeintimeTemp, 1);
            	if(compare(nowDate,storeintimeTemp) ){//一整天24小时的计算
           		     moneyTotal= moneyTotal+ cost( storeintimeTempUp,  storeintimeTemp,   boxtypecode,   chargeModeInt);
	           	}else{//不到一天的计算
	           		 moneyTotal= moneyTotal+ cost( storeintimeTempUp,  nowDate,   boxtypecode,   chargeModeInt);
	           		 break;
	           	}
		    }while(compare(nowDate,storeintimeTemp));    
 
		    System.out.println("1-----moneyTotal="+moneyTotal);
   }

    public static int   cost(java.util.Date storeintime,java.util.Date nowDate, Integer boxtypecode, Integer chargeModeInt){
    	 return 1;
    }
    
    
    
    public static String nowDate()
    {
        return nowDate("-");
    }

    public static String nowDate(String sper)
    {
        GregorianCalendar Time = new GregorianCalendar();
        String s_nowD = "";
        String s_nowM = "";
        if(sper == null)
            sper = "-";
        else
            sper = sper.trim();
        int nowY = Time.get(1);
        int nowM = Time.get(2) + 1;
        if(nowM < 10)
            s_nowM = (new StringBuilder("0")).append(nowM).toString();
        else
            s_nowM = (new StringBuilder()).append(nowM).toString();
        int nowD = Time.get(5);
        if(nowD < 10)
            s_nowD = (new StringBuilder("0")).append(nowD).toString();
        else
            s_nowD = (new StringBuilder()).append(nowD).toString();
        String result = (new StringBuilder(String.valueOf(nowY))).append(sper).append(s_nowM).append(sper).append(s_nowD).toString();
        return result;
    }

    public static String nowTime()
    {
        return nowTime(":");
    }

    public static String nowTime(String sperate)
    {
        GregorianCalendar Time = new GregorianCalendar();
        int i_hour = Time.get(10);
        int i_tag = Time.get(9);
        if(i_tag == 1 && i_hour < 12)
            i_hour += 12;
        int i_minutes = Time.get(12);
        int i_seconds = Time.get(13);
        int i_mill = Time.get(14);
        String s_time = "";
        if(i_hour >= 0 && i_hour < 10)
            s_time = "0";
        s_time = (new StringBuilder(String.valueOf(s_time))).append(i_hour).append(sperate).toString();
        if(i_minutes >= 0 && i_minutes < 10)
            s_time = (new StringBuilder(String.valueOf(s_time))).append("0").toString();
        s_time = (new StringBuilder(String.valueOf(s_time))).append(i_minutes).append(sperate).toString();
        if(i_seconds >= 0 && i_seconds < 10)
            s_time = (new StringBuilder(String.valueOf(s_time))).append("0").toString();
        s_time = (new StringBuilder(String.valueOf(s_time))).append(i_seconds).append(".").append(i_mill).toString();
        return s_time;
    }

    public static int getDayCountFromYearMonth(int nYear, int nMonth)
    {
        Calendar c = Calendar.getInstance();
        c.set(1, nYear);
        c.set(5, 1);
        c.set(2, nMonth - 1);
        return c.getActualMaximum(5);
    }

    public static Date changeMonth(Date date, int month)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(2, month);
        Date newDate = new Date(cal.getTime().getTime());
        return newDate;
    }

    public static Calendar getUTCCal()
    {
        Calendar utcCal = null;
        utcCal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        utcCal.setTimeZone(tz);
        return utcCal;
    }

    public static Timestamp convert2UTCTimestamp(Timestamp value)
    {
        Calendar defaultCal = Calendar.getInstance();
        defaultCal.setTime(value);
        Calendar cal = getUTCCal();
        cal.set(1, defaultCal.get(1));
        cal.set(2, defaultCal.get(2));
        cal.set(5, defaultCal.get(5));
        cal.set(11, defaultCal.get(11));
        cal.set(12, defaultCal.get(12));
        cal.set(13, defaultCal.get(13));
        return new Timestamp(cal.getTime().getTime());
    }

    public static Date convert2UTCDate(Date value)
    {
        Calendar defaultCal = Calendar.getInstance();
        defaultCal.setTime(value);
        Calendar cal = getUTCCal();
        cal.set(1, defaultCal.get(1));
        cal.set(2, defaultCal.get(2));
        cal.set(5, defaultCal.get(5));
        return new Date(cal.getTime().getTime());
    }

    public static Date toSQLDate(Timestamp timestamp)
    {
        long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
        return new Date(milliseconds);
    }

    public static Date toSQLDate(java.util.Date date)
    {
        return new Date(date.getTime());
    }

    public String getFormatedDateString(int timeZoneOffset)
    {
        if(timeZoneOffset > 13 || timeZoneOffset < -12)
            timeZoneOffset = 0;
        String ids[] = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
        TimeZone timeZone;
        if(ids.length == 0)
            timeZone = TimeZone.getDefault();
        else
            timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        return sdf.format(new java.util.Date());
    }

    public static String dateToString(java.util.Date date, String _timeZone)
    {
        if(date == null)
            return "";
        TimeZone timeZone = null;
        if(StringUtils.isEmpty(_timeZone))
            timeZone = TimeZone.getDefault();
        else
            timeZone = TimeZone.getTimeZone(_timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    public static String dateToString(java.util.Date date)
    {
        return dateToString(date, null);
    }

    public static String dateToString1(java.util.Date date)
    {
        if(date == null)
        {
            return "";
        } else
        {
            TimeZone timeZone = null;
            timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String dateToString2(java.util.Date date)
    {
        if(date == null)
        {
            return "";
        } else
        {
            TimeZone timeZone = null;
            timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String dateToString(Date date, String _timeZone)
    {
        return dateToString(new java.util.Date(date.getTime()), _timeZone);
    }

    public static String dateToString(Date date)
    {
        return dateToString(new java.util.Date(date.getTime()));
    }

    public static String timestampToString(java.util.Date date, String _timeZone)
    {
        if(date == null)
            return "";
        TimeZone timeZone = null;
        if(StringUtils.isEmpty(_timeZone))
            timeZone = TimeZone.getDefault();
        else
            timeZone = TimeZone.getTimeZone(_timeZone);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    public static String timestampToDateString(java.util.Date date)
    {
        if(date == null)
        {
            return "";
        } else
        {
            TimeZone timeZone;
            timeZone = timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String timestampToString(java.util.Date date)
    {
        return timestampToString(date, null);
    }

    public static String timestampToString(Timestamp timestamp, String _timeZone)
    {
        long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
        return timestampToString(new java.util.Date(milliseconds), _timeZone);
    }

    public static String timestampToString(Timestamp timestamp)
    {
        long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
        return timestampToString(new java.util.Date(milliseconds));
    }

    public static String timestampToGMTString(Timestamp timestamp)
    {
        if(timestamp == null)
        {
            return "";
        } else
        {
            TimeZone timeZone = null;
            timeZone = TimeZone.getTimeZone("GMT:00");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(timeZone);
            long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
            return (new StringBuilder(String.valueOf(sdf.format(new java.util.Date(milliseconds))))).append(" GMT").toString();
        }
    }

    public static Date getMinDate()
    {
        return minDate;
    }

    public static java.util.Date addDate(java.util.Date date, int day)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + (long)day * 24L * 3600L * 1000L);
        return c.getTime();
    }

    public static Date addDate(Date date, int day)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + (long)day * 24L * 3600L * 1000L);
        return new Date(c.getTime().getTime());
    }

    public static Timestamp addTimeStamp(Timestamp date, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)day * 24L * 3600L * 1000L);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp decreaseSysDateTime(Timestamp date, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)day * 24L * 3600L * 1000L);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        Timestamp sysDateTime = new Timestamp(cal.getTime().getTime());
        return sysDateTime;
    }

    public static Timestamp addTimeStamp(Timestamp date, double day)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)(day * 24D * 3600D * 1000D));
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp addTimeStampBySecond(Timestamp date, int second)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)second * 1000L);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp addTimeStampByHour(Timestamp date, int hour)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)hour * 3600L * 1000L);
        return new Timestamp(cal.getTime().getTime());
    }

    public static int diffDate(java.util.Date date, java.util.Date date1)
    {
        return (int)((getMillis(date) - getMillis(date1)) / 86400000L);
    }

    public static int diffHour(java.util.Date start, java.util.Date end)
    {
        return (int)((getMillis(end) - getMillis(start)) / 3600000L);
    }

    public static int diffHour(Timestamp start, Timestamp end)
    {
        return (int)((getMillis(end) - getMillis(start)) / 3600000L);
    }

    public static int diffMinute(java.util.Date start, java.util.Date end)
    {
        return (int)((getMillis(end) - getMillis(start)) / 60000L);
    }

    public static long getMillis(java.util.Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static Date stringToDate(String value, String fmt)
    {
        SimpleDateFormat sd = new SimpleDateFormat(fmt);
        java.util.Date date = null;
        try
        {
            date = sd.parse(value);
            return new Date(date.getTime());
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Date stringToDate(String value)
    {
        return stringToDate(value, "yyyyMMdd");
    }

    public static Date getUTCBeginDate()
    {
        return stringToDate("19700101");
    }

    public static Timestamp stringToTimestamp(String value, String fmt)
    {
        SimpleDateFormat sd = new SimpleDateFormat(fmt);
        java.util.Date date = null;
        try
        {
            date = sd.parse(value);
            return new Timestamp(date.getTime());
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp stringToTimestamp(String value)
    {
        return stringToTimestamp(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static int currentYear()
    {
        return Calendar.getInstance().get(1);
    }

    public static int currentMonth()
    {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int getHour(java.util.Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }


    public static Date minDate = null;

    static 
    {
        Calendar c = Calendar.getInstance();
        c.set(1, 1900);
        c.set(5, 1);
        c.set(2, 0);
        minDate = new Date(c.getTime().getTime());
    }
}
