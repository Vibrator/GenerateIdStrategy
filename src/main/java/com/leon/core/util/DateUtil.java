package com.leon.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhh");
	
	private static SimpleDateFormat pdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
	public static Date parse(String str)
	{
		Date date = null;
		try {
			date = pdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String format(Date date, String ft)
	{
		SimpleDateFormat s = new SimpleDateFormat(ft);
		return s.format(date);
	}
	
	public static String format(Date date)
	{
		return sdf.format(date);
	}
	
	
	
	/**
	 * 判断str是否为yyyy-mm-dd的时间格式，是怎返回true;否返回false
	 * @param str
	 * @return
	 */
	public static boolean isDateFormat(String str)
	{
		Pattern p = Pattern.compile("^[1-2][0-9]{3}-(0[1-9]|1[0-2])-([0-2][1-9]|3[0-1])$"); 
		Matcher matcher = p.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 获取yyMMddhh的时间串
	 */
	public static String getDateSequence()
	{
		return sdf.format(new Date());
	}
	
	public static String getHHMMSS_SSS(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss_SSS");
		return sdf.format(date);
	}
	
	public static String getYYYYMMDDHHMMSS(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	public static String getYYYYMMDD(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	public static Long getCurrentTime(){
		return new Date().getTime();
	}
	
	public static String getYY_MM_DDHHmmss(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	

	public static String getYY_MM_DDHHmm(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}
	
	public static String getYY_MM_DD(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static Date getDateFormat(String dateStr,String dateFormat){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try{
			date = sdf.parse(dateStr);
		}catch(Exception e){
			return null;
		}
		return date;
	}
	
	public static Date attemptFormat(String dateStr){
		if(dateStr.contains("T")){
			return getDateFormat(dateStr.substring(0, 10)+" "+dateStr.substring(11, 19),"yyyy-MM-dd HH:mm:ss");
		}
		if(getDateFormat(dateStr,"yyyy-MM-dd HH:mm:ss") != null){
			return getDateFormat(dateStr,"yyyy-MM-dd HH:mm:ss");
		}
		if(getDateFormat(dateStr,"yyyy-MM-dd HH:mm") != null){
			return getDateFormat(dateStr,"yyyy-MM-dd HH:mm");
		}
		if(getDateFormat(dateStr,"yyyy-MM-dd") != null){
			return getDateFormat(dateStr,"yyyy-MM-dd");
		}
		return null;
	}
	
	/**
	 * 对字符串处理，返回格式：yy/MM/dd HH:mm:ss
	 * @return
	 */
	public static String attemptFormatStr(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = attemptFormat(dateStr);
		if(date != null){
			return sdf.format(date);
		}
		return null;
	}
	
	/**
	 * Unix时间戳转换
	 * 返回格式：yyyy/MM/dd HH:mm:ss
	 * @return
	 */
	public static String unixTimestampFormat(String dateStr) {
		Long timeStamp = Long.valueOf(dateStr)*1000;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp)))); // 时间戳转换成时间
		return sd;
	}
	
	/**
	 * 根据系统时间，返回上周最后一天，周一算一周的第一天
	 * @return
	 */
	public static java.sql.Date getLastWeekLastDay(){
		Calendar monday = Calendar.getInstance();
		monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		monday.add(Calendar.DATE, -1);
		java.sql.Date date = new java.sql.Date(monday.getTimeInMillis());
		return date;
	}
	
	/**
	 * 根据系统时间，返回下周第一天，周一算一周的第一天
	 * @return
	 */
	public static java.sql.Date getNextWeekFirstDay(){
		Calendar sunday = Calendar.getInstance();
		sunday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		sunday.add(Calendar.DATE, 7);
		java.sql.Date date = new java.sql.Date(sunday.getTimeInMillis());
		return date;
	}
}
