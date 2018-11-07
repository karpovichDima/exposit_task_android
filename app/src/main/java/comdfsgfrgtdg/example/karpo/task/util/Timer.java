package comdfsgfrgtdg.example.karpo.task.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer {

    public static String getCurrentDateTime(){
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(currentDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static Date convertStringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat();
        Date convertedDate;
        try {
            convertedDate = formatter.parse(date);
            return convertedDate;
        } catch (ParseException e) {
            return new Date(18/12/2000);
        }
    }
}
