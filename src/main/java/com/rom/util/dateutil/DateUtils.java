package com.rom.util.dateutil;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *   时间工具类
 * User: Administrator
 * Date: 2010-8-10
 * Time: 9:33:57
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    public static final String DIGIT14DATE = "yyyy-MM-dd HH:mm:ss";

    public static final String DIGIT8DATE = "yyyy-MM-dd";

    public static final String DATEFORMAT = "dd/MM/yyyy:HH:mm:ss";

    public static final Map<String,String> monthMap = setupMonth();

    /**
     * 将月份放入Map
     * @return map
     */
    public static Map<String,String> setupMonth() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("Jan", "01");
        map.put("Feb", "02");
        map.put("Mar", "03");
        map.put("Apr", "04");
        map.put("May", "05");
        map.put("Jun", "06");
        map.put("Jul", "07");
        map.put("Aug", "08");
        map.put("Sep", "09");
        map.put("Oct", "10");
        map.put("Nov", "11");
        map.put("Dec", "12");
        return map;
    }

    /**
     * 获得系统当前日期
     * @param format 日期转换格式
     * @return 转换后的日期
     */
    public static String getNowDate(String format) {
        Date dateNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(dateNow);
    }

    /**
     * 日期加，减运算
     * @param date   日期
     * @param count  加，减的数量
     * @param format 日期转换格式
     * @return 增加或相减后的日期
     */
    public static String addDateDay(Date date, int count, String format) {
        return parseDate(addDateDay(date, count), format);
    }

    /**
     * 日期加，减运算
     * @param date  日期
     * @param count 加，减的数量
     * @return 增加或相减后的日期
     */
    public static Date addDateDay(Date date, int count) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, count);
        return calendar.getTime();
    }

    /**
     * 日期格式转换
     * @param date 日期
     * @param format 日期转换格式
     * @return 格式化后的日期
     */
    public static String parseDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
    
    /**
     * 用定义的日期格式，把一个字符串解析为日期类型
     * @param date 当前传入日期字符串格式
     * @param format 格式化字符串(类型)yyyy-MM-dd、yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期，也就是将一个为日期格式的字符串转换成Date类型的
     */
    public static Date parserText(String date, String format) {
        SimpleDateFormat simpleDateFormat = null;
        if (format != null && !"".equals(format))
            simpleDateFormat = new SimpleDateFormat(format);
        else {
            simpleDateFormat = new SimpleDateFormat();
        }
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public static Date parserText(String date) {
        return parserText(date, null);
    }

    public static String getDigitDate(String month) {
        return (String) monthMap.get(month);
    }

    public static int getDayOfMonth(Date d){
        Calendar c1 =  Calendar.getInstance();
                c1.setTime(d);
        return c1.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(Date d){
        Calendar c1 =  Calendar.getInstance();
                c1.setTime(d);
        return c1.get(Calendar.MONTH)+1;
    }

    public static int getDayCountInMonth(Date d){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.set(Calendar.DAY_OF_MONTH,1);
       // System.out.println(calendar.getTime());
        calendar.roll(Calendar.DATE, false);
        return calendar.get(Calendar.DATE);
    }

    public static String now8(){
        return parseDate(new Date(),DIGIT8DATE);
    }

    
    public static String add(String date , int i){
        Date d = parserText(date,DIGIT8DATE);
        d = addDateDay(d,i);
        return parseDate(d,DIGIT8DATE);
    }

    /**
     * 获取date日期上month增加后的日期
     * @param date 当前传入日期
     * @param count 增加值
     * @return 增加后的日期
     */
    public static Date addMonth(Date date, int count) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTime();
    }
    
    /**
     * 获取date日期上month增加后的日期  format 指定返回日期的格式
     * @param date 当前传入日期
     * @param count 增加值
     * @return 处理后的日期，增加月，格式化日期
     */
    public static String addMonth(Date date, int count, String format) {
        return parseDate(addMonth(date, count), format);
    }
    
    /**
	 *    查询当前时间 之后 多少分钟的时间
	 *      例如： 当前时间为：2015-10-15 22:05:11
	 *           要查询之后30分钟的时间
	 *           返回结果为：2015-10-15 22:35:11
	 * @param minute  之后分钟间隔
	 * @return
	 */
	String getNowAfterTime(int minute){
		Date nowdate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DIGIT14DATE);
		long nowstamp = nowdate.getTime();
		long endstamp = nowstamp+(minute*60*1000);
		
		String date = sdf.format(new Date(endstamp));
		System.out.println(date);
		return date;
	}
}
