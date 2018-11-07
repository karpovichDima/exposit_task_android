package comdfsgfrgtdg.example.karpo.task.presenter;

import java.util.List;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.data.FireBase;
import comdfsgfrgtdg.example.karpo.task.view.AuthActivity;

import static comdfsgfrgtdg.example.karpo.task.util.Validator.isFieldNotEmpty;

public class AuthPresenter {

    public static String SEPARATOR;
    public static String PROFILE_DIRECTORY;
    public static String AGE_DIRECTORY;
    public static String GENDER_DIRECTORY;
    public static String LAST_NAME_DIRECTORY;
    public static String NAME_DIRECTORY;
    public static String FAILED_SIGN_IN_STATUS;
    public static String SUCCESS_SIGN_IN_STATUS;
    public static String SUCCESS_REGISTRATION_STATUS;
    public static String FAILED_REGISTRATION_STATUS;
    public static String POST_DIRECTORY;
    public static String FIRST_MESSAGE;
    public static String TEXT_DIRECTORY;
    public static String DATE_DIRECTORY;
    static String LAST_NAME;
    static String AGE_HINT;
    static String NAME;
    static String GENDER;
    private AuthView authView;
    private FireBase fireBase;
    private AuthActivity authActivity;

    public AuthPresenter(AuthView authView, AuthActivity authActivity) {
        this.authActivity = authActivity;
        this.authView = authView;
        fireBase = new FireBase(this);
        initConstants();
    }

    private void initConstants() {
        SEPARATOR = authActivity.getString(R.string.separator);
        PROFILE_DIRECTORY = authActivity.getString(R.string.profile_directory);
        AGE_DIRECTORY = authActivity.getString(R.string.age_directory);
        GENDER_DIRECTORY = authActivity.getString(R.string.gender_directory);
        LAST_NAME_DIRECTORY = authActivity.getString(R.string.last_name_directory);
        NAME_DIRECTORY = authActivity.getString(R.string.name);
        FAILED_SIGN_IN_STATUS = authActivity.getString(R.string.failed_sign_in_status);
        SUCCESS_SIGN_IN_STATUS = authActivity.getString(R.string.success_sign_in_status);
        SUCCESS_REGISTRATION_STATUS = authActivity.getString(R.string.success_registration_status);
        FAILED_REGISTRATION_STATUS = authActivity.getString(R.string.failed_registration_status);
        POST_DIRECTORY = authActivity.getString(R.string.post_directory);
        FIRST_MESSAGE = authActivity.getString(R.string.first_message_text);
        TEXT_DIRECTORY = authActivity.getString(R.string.text_directory);
        DATE_DIRECTORY = authActivity.getString(R.string.date_directory);
        LAST_NAME = authActivity.getString(R.string.lastName);
        AGE_HINT = authActivity.getString(R.string.age_hint);
        NAME = authActivity.getString(R.string.name);
        GENDER = authActivity.getString(R.string.gender);






    }

    public void signIn(String email, String password, AuthActivity authActivity) {
        fireBase.signIn(email, password, authActivity);
    }

    public boolean isValidFields(List<String> fields) {
        boolean fieldNotEmpty;
        for (String field : fields) {
            fieldNotEmpty = isFieldNotEmpty(field);
            if (!fieldNotEmpty) return false;
        }
        return true;
    }

    public void sendToast(String status) {
        authView.showToast(status);
    }

    public void startListMessageActivity() {
        authView.queryToStartListMassageActivity();
    }

    public interface AuthView {
        void showToast(String text);
        void queryToStartListMassageActivity();
    }
}
