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

    public static double getFormattedString(String string){

        try {
            return DecimalFormat.getNumberInstance().parse(string).doubleValue();

        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }

    }
}
