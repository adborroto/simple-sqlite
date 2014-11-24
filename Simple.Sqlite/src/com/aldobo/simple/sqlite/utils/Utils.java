package com.aldobo.simple.sqlite.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.aldobo.simple.sqlite.entities.Field;
import com.aldobo.simple.sqlite.entities.SQLiteType;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class Utils {

    public static void putInContentValue(ContentValues cv,Field f,Object value)
    {
        switch (f.getType())
        {
            case INT:
                cv.put(f.getName(),(Integer)value);
                return;
            case VARCHAR:
                cv.put(f.getName(),(String)value);
                return;
            case DATETIME:
                String date= Utils.parseDate((Date)value);
                cv.put(f.getName(),date);
                return;
            case BLOB:
                cv.put(f.getName(),(byte[])value);
                return;
            case BOOLEAN:
                cv.put(f.getName(),(Boolean)value);
                return;
            case TEXT:
                cv.put(f.getName(),(String)value);
                return;
            case FLOAT:
                cv.put(f.getName(),(Float)value);
                return;
            case DOUBLE:
                cv.put(f.getName(),(Double)value);
                return;
            case REAL:
                cv.put(f.getName(),(Double)value);
                return;
        }
    }

    public static Object getValue(Cursor cursor,Field modelField,int index)
    {
        switch (modelField.getType())
        {
            case INT:
                return cursor.getInt(index);
            case DATETIME:
                String date = cursor.getString(index);
                return Utils.parseStringToDate(date);
            case VARCHAR:
                return cursor.getString(index);
            case BLOB:
                return cursor.getBlob(index);
            case BOOLEAN:
                return cursor.getShort(index)==0?Boolean.FALSE:Boolean.TRUE;
            case FLOAT:
                return cursor.getFloat(index);
            case DOUBLE:
                return cursor.getDouble(index);
            case TEXT:
                return cursor.getString(index);
            case REAL:
                return cursor.getDouble(index);
            default:return null;
        }
    }

    public static <T> SQLiteType getSQLiteType(Class<T> type)
    {
        if(type == Integer.class)
            return SQLiteType.INT;
        else if(type == String.class)
            return SQLiteType.TEXT;
        else if(type == byte[].class)
            return SQLiteType.BLOB;
        else if(type == Boolean.class)
            return SQLiteType.BOOLEAN;
        else if(type == Double.class)
            return SQLiteType.DOUBLE;
        else if(type == Date.class)
            return SQLiteType.DATETIME;
        else if(type == Float.class)
            return SQLiteType.FLOAT;
        return null;
    }

    public static Boolean isNullOrEmpty(String s)
    {
        return s==null || s=="";
    }

    public static String parseDate(Date date)
    {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer buffer= iso8601Format.format(date,new StringBuffer(),null);
        String value = buffer.toString();
        //TODO: fix this. find a better way
        return String.format("%s/%s/%s",date.getDate(),date.getMonth(),date.getYear());
    }
    public static Date parseStringToDate(String dateTime)
    {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = iso8601Format.parse(dateTime);
            return date;
        } catch (ParseException e) {
        }
        return null;
    }
}
