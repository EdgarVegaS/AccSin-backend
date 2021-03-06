package com.accsin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtils {

    public static Date getDateNextMonth(String number){
        int n = Integer.parseInt(number);
        if (n > getLastDayOfNextMonth()) {
            n = getLastDayOfNextMonth();
        }
        String monthYearNextMonth = getStringNextMonth();
        String dateString = String.format("%s/%s",number,monthYearNextMonth);
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);    
            return date;
        } catch (Exception e) {
            return null;      
        }
    }

    public static String getLastYearMonthString(){

        DateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static String getLastMonthString(){

        DateFormat df = new SimpleDateFormat("MMMM");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    public static String getLastMonthString1(){

        DateFormat df = new SimpleDateFormat("MMMM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        return df.format(calendar.getTime());
    }

    public static String getActualYearMonthNumber(){

        DateFormat df = new SimpleDateFormat("yyyy/MM");
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }


    public static Date getExpirationDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 10);
        return calendar.getTime();

    }

    public static int getLastDayOfNextMonth(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String getStringNextMonth(){
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar  = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        Date date = calendar.getTime();
        return dt.format(date);
    }

    public static String getStringActualDate(){
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar  = Calendar.getInstance();
        Date date = calendar.getTime();
        return dt.format(date);
    }

    public static Date parseStringToDate(String dateString){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static Date parseStringToDateTime(String dateString){
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = df.parse(dateString);
            return date;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static String getHourString(Date date){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(date);
    }

    public static String getDateFormatFromDate(Date date){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }

    public static String getMonthYear(String date){
        return date.substring(0,7);
    }  
}