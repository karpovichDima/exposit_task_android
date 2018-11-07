package comdfsgfrgtdg.example.karpo.task.util;

import android.annotation.SuppressLint;
import android.text.Editable;

import com.google.firebase.database.GenericTypeIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Util {

    public static int convertStringToInt(String value) {
        return Integer.parseInt(value);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateToString(Date date) {
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }

    public static String generateIdForPost(){
        UUID uuid = UUID.randomUUID();
        return "post_" + uuid;
    }

    public static GenericTypeIndicator<String> getGenericString(){
        return new GenericTypeIndicator<String>(){};
    }
}
