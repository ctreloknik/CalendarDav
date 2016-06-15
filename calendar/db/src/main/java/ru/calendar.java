package ru;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;


public class calendar
{

    @Test
    public void test()
    {
        Calendar start = new GregorianCalendar();
        Calendar date = new GregorianCalendar();
        Calendar end = new GregorianCalendar();
        
        start.set(2016, 4, 9);
        end.set(2016, 4, 13);
        date.set(2016, 4, 26);
        
        Long weeks = (date.getTime().getTime() - start.getTime().getTime())/604800000;
        System.out.println(weeks.toString());
        
        date.add(Calendar.DAY_OF_YEAR, (int) (-weeks*7));
        System.out.println(date.getTime());
        
    }

}
