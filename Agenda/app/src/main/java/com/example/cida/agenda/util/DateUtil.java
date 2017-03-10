package com.example.cida.agenda.util;

import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AdilsonMarcelino on 21/07/2016.
 */
public class DateUtil {

    public static Date getDate(int year, int monthOfYear, int dayOfMonth)

    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear,dayOfMonth);
        Date data = calendar.getTime();


        return data;

    }



public static String dateToString(int year, int monthOfYear, int dayOfMonth)

{
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthOfYear,dayOfMonth);
    Date data = calendar.getTime();

    DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
    String  dt = format.format(data);
    return dt;

}


}
