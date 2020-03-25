package my.amir.corona.Helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static Date getDate(String dateTime){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{

            return format.parse(dateTime);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static double getFormattedStringToDouble(String string){

        try {
            return DecimalFormat.getNumberInstance().parse(string).doubleValue();

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public static int getFormattedStringToLong(String string){

        try {
            return DecimalFormat.getNumberInstance().parse(string).intValue();

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }

    public static String getDateToString(String dateTime){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{

            Date date = format.parse(dateTime);

            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();
            int hour = date.getHours();
            int minute = date.getMinutes();

            return year+"-"+month+"-"+day+" "+hour+":"+minute;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
