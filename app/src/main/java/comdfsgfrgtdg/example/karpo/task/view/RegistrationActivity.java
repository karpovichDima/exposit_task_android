package comdfsgfrgtdg.example.karpo.task.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.presenter.ProfilePresenter;
import comdfsgfrgtdg.example.karpo.task.presenter.RegistrationPresenter;

public class RegistrationActivity extends AppCompatActivity implements RegistrationPresenter.RegistrationView{

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextLastName;
    private EditText editTextName;
    private EditText editTextAge;
    private RadioGroup editTextRadioGroup;
    private RegistrationPresenter registrationPresenter;
    private List<String> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        initLayoutComponent();
        registrationPresenter = new RegistrationPresenter(this);
        fields = new ArrayList<>();
    }

    public void registrationButtonListener(View view){
        getTextFromFields();
        boolean isValid = registrationPresenter.validationFieldsProfile(fields);
        if (!isValid) {
            Toast.makeText(RegistrationActivity.this, getString(R.string.not_valid_data), Toast.LENGTH_SHORT).show();
            return;
        }
        registrationPresenter.registration(editTextEmail.getText().toString(), editTextPassword.getText().toString(), this);
    }

    private void getTextFromFields() {
        fields.add(getAgeText());
        fields.add(getNameText());
        fields.add(getLastNameText());
        fields.add(getSelectGender());
        fields.add(editTextEmail.getText().toString());
        fields.add(editTextPassword.getText().toString());
    }

    private void initLayoutComponent() {
        editTextEmail = (EditText) findViewById(R.id.input_email_reg);
        editTextPassword = (EditText) findViewById(R.id.input_password_reg);
        editTextLastName = (EditText) findViewById(R.id.input_last_name_reg);
        editTextName = (EditText) findViewById(R.id.input_first_name_reg);
        editTextAge = (EditText) findViewById(R.id.input_age_reg);
        editTextRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
    }

    public void startListMessageActivity() {
        Intent intent = new Intent(RegistrationActivity.this, ListPostsActivity.class);
        startActivity(intent);
    }

    public String getSelectGender() {
        int selectedId = editTextRadioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioGender = (RadioButton) findViewById(selectedId);
            return radioGender.getText() + "";
        }
        return "";
    }

    @Override
    public String getAgeText() {
        return editTextAge.getText().toString();
    }

    @Override
    public String getGenderText() {
        return getSelectGender();
    }

    @Override
    public String getLastNameText() {
        return editTextLastName.getText().toString();
    }

    @Override
    public String getNameText() {
        return editTextName.getText().toString();
    }

    @Override
    public void queryToCreateToast(String text) {
        Toast.makeText(RegistrationActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void queryToStartCompletingRegistrationActivity() {
        finish();
        startListMessageActivity();
    }
}
