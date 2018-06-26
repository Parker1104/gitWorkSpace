package com.hzdongcheng.softwareplatform.intelligentstorage.bll.util;

import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;
import java.util.Date;  
  
/** 
 * 时间对比 
 *  
 */  
public class CompareTime {  
      
    /** 时间格式                        */  
    private static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";  
      
    /** 
     * 两时间对比, 相差是否超过3秒 
     *  
     * @param dateA 
     * @param dateB 
     * @return 
     * @throws ParseException 
     */  
    public static boolean compare(String dateA, String dateB,int second)   {  
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);  
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
    public static boolean compare(Date dateA, Date dateB,int second) throws ParseException {  
        DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);  
        //boolean   sTime= Math.abs( df.parse(dateA).getTime() - df.parse(dateB).getTime()) > 3000;
        boolean   secondTime=  ( dateA.getTime() -dateB.getTime()) > second;
        return secondTime;  
    } 
    /** 
     * 主函数 
     *  
     * @param args 
     * @throws ParseException 
     */  
    public static void main(String[] args) throws ParseException {  
         boolean isExceed = compare("2010-01-05 22:22:25", "2010-01-05 22:22:21",3000);  
         System.out.println("两个时间相比, 是否相差超过3秒：" + isExceed);  

         SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
         Date date=formatter.parse("2017-08-28 08:48:10");  
 
         DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);  
         //boolean   sTime= Math.abs( df.parse(dateA).getTime() - df.parse(dateB).getTime()) > 3000;
         boolean secondTime=false;
 		 try {
 			System.out.println("----"+( df.parse(date.toLocaleString()).getTime() - df.parse(new Date().toLocaleString()).getTime()) );
 			secondTime = ( df.parse(date.toLocaleString()).getTime() - df.parse(new Date().toLocaleString()).getTime()) > 1000;
 		 } catch (ParseException e) {
 			e.printStackTrace();
 		 }
 
    }  
  
}








