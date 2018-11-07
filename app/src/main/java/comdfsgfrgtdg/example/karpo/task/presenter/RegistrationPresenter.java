package comdfsgfrgtdg.example.karpo.task.presenter;

import java.util.ArrayList;
import java.util.List;

import comdfsgfrgtdg.example.karpo.task.data.FireBase;
import comdfsgfrgtdg.example.karpo.task.view.RegistrationActivity;

import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.AGE_HINT;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.GENDER;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.LAST_NAME;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.NAME;
import static comdfsgfrgtdg.example.karpo.task.util.Validator.isDigit;
import static comdfsgfrgtdg.example.karpo.task.util.Validator.isFieldNotEmpty;

public class RegistrationPresenter {

    private FireBase fireBase;
    private RegistrationView registrationView;
    private List<String> fieldsValue;
    private List<String> partOfLink;

    public RegistrationPresenter(RegistrationView registrationView){
        this.registrationView = registrationView;
        fireBase = new FireBase(this);
        fieldsValue = new ArrayList<>();
        partOfLink = new ArrayList<>();

        partOfLink.add(NAME);
        partOfLink.add(LAST_NAME);
        partOfLink.add(GENDER);
        partOfLink.add(AGE_HINT);
    }

    public boolean validationFieldsProfile(List<String> fields){
        for (String field : fields) {
            boolean fieldNotEmpty = isFieldNotEmpty(field);
            if (!fieldNotEmpty) {
                fields.clear();
                return false;
            }
        }
        String firstElement = fields.get(0);
        boolean digit = isDigit(firstElement);
        if (!digit) {
            fields.clear();
            return false;
        }
        fields.clear();
        return true;
    }

    public void settingProfile(String currentUserUid) {
        fieldsValue.add(registrationView.getNameText());
        fieldsValue.add(registrationView.getLastNameText());
        fieldsValue.add(registrationView.getGenderText());
        fieldsValue.add(registrationView.getAgeText());
        fireBase.setValue(currentUserUid, fieldsValue, partOfLink);
    }

    public void registration(String email, String password, RegistrationActivity registrationActivity) {
        fireBase.createNewFireBaseUser(email, password, registrationActivity);
    }

    public void createToast(String text) {
        registrationView.queryToCreateToast(text);
    }

    public void startActivityCompletingRegistration() {
        registrationView.queryToStartCompletingRegistrationActivity();
    }

    public interface RegistrationView {
        String getAgeText();
        String getGenderText();
        String getLastNameText();
        String getNameText();
        void queryToCreateToast(String text);
        void queryToStartCompletingRegistrationActivity();
    }

}
