package comdfsgfrgtdg.example.karpo.task.util;

import android.text.Editable;

public class Validator {

    public static boolean isFieldNotEmpty(String fieldText) {
        if (fieldText.length() < 1) return false;
        return true;
    }

    public static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLessThanHundred(Editable text) {
        int length = text.length();
        if (length > 100 || length < 1) return false;
        return true;
    }

}
