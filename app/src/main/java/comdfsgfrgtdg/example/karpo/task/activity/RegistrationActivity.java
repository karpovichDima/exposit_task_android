package comdfsgfrgtdg.example.karpo.task.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import comdfsgfrgtdg.example.karpo.task.R;

import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.DATE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.POST_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.SEPARATOR;

public class RegistrationActivity extends AppCompatActivity {

    public static String PROFILE_DIRECTORY;
    public static String TEXT_DIRECTORY;
    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextLastName;
    private EditText editTextName;
    private EditText editTextAge;
    private RadioGroup editTextRadioGroup;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        initLayoutComponent();
        initConstants();

        final Button button = (Button) findViewById(R.id.btn_login_reg);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isValid = validationFieldsProfile();
                if (!isValid) return;
                registration();
            }
        });
    }

    private boolean validationFieldsProfile(){
        Editable textLastName = editTextLastName.getText();
        Editable textName = editTextName.getText();
        if (textLastName.length() < 1 || textName.length() < 1){
            Toast.makeText(RegistrationActivity.this, getString(R.string.empty_field_exception), Toast.LENGTH_SHORT).show();
            return false;
        }
        Editable textAge = editTextAge.getText();
        boolean digit = isDigit(textAge + "");
        if (!digit) {
            Toast.makeText(RegistrationActivity.this, getString(R.string.not_number_exception), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initLayoutComponent() {
        editTextEmail = (EditText) findViewById(R.id.input_email_reg);
        editTextPassword = (EditText) findViewById(R.id.input_password_reg);
        editTextLastName = (EditText) findViewById(R.id.input_last_name_reg);
        editTextName = (EditText) findViewById(R.id.input_first_name_reg);
        editTextAge = (EditText) findViewById(R.id.input_age_reg);
        editTextRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
    }

    public void registration() {
        String email = getEmail();
        String password = getPassword();
        boolean fieldNotEmpty = isFieldNotEmpty(email, password);
        if (!fieldNotEmpty){
            Toast.makeText(RegistrationActivity.this, getString(R.string.text_login_exception), Toast.LENGTH_SHORT).show();
            return;
        }
        createNewFireBaseUser(email, password);
    }

    private boolean isFieldNotEmpty(String email, String password) {
        int emailLength = email.length();
        int passwordLength = password.length();
        if (emailLength < 1 || passwordLength < 1) return false;
        return true;
    }

    private void createNewFireBaseUser(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.signInWithEmailAndPassword(email, password);
                    String currentUserUid = mAuth.getCurrentUser().getUid();
                    settingProfile(currentUserUid);
                    setFirstMessage(currentUserUid);
                    Toast.makeText(RegistrationActivity.this, "Success registration", Toast.LENGTH_SHORT).show();
                    startListMessageActivity();
                } else
                    Toast.makeText(RegistrationActivity.this, "Failed registration", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settingProfile(String currentUserUid) {
        setAge(currentUserUid);
        setGender(currentUserUid);
        setLastName(currentUserUid);
        setName(currentUserUid);
    }

    private void setName(String currentUserUid) {
        DatabaseReference myRef = null;
        myRef = database.getReference(currentUserUid + SEPARATOR + PROFILE_DIRECTORY + SEPARATOR +
                getString(R.string.name));
        myRef.setValue(editTextName.getText() + "");
    }

    private void setLastName(String currentUserUid) {
        DatabaseReference myRef = null;
        myRef = database.getReference(currentUserUid + SEPARATOR + PROFILE_DIRECTORY + SEPARATOR +
                getString(R.string.last_name_directory));
        myRef.setValue(editTextLastName.getText() + "");
    }

    private void setGender(String currentUserUid) {
        DatabaseReference myRef = null;
        String selectGender = getSelectGender();
        myRef = database.getReference(currentUserUid + SEPARATOR + PROFILE_DIRECTORY + SEPARATOR +
                getString(R.string.gender_directory));
        myRef.setValue(selectGender);
    }

    private void setAge(String currentUserUid) {
        DatabaseReference myRef;
        myRef = database.getReference(currentUserUid + SEPARATOR + PROFILE_DIRECTORY + SEPARATOR +
                getString(R.string.age_directory));
        boolean digit = isDigit(editTextAge.getText() + "");
        if (digit){
            myRef.setValue(editTextAge.getText() + "");
        } else {
            myRef.setValue("18");
        }
    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void setFirstMessage(String currentUserUid) {
        DatabaseReference myRef;
        String generatedPostId = generateIdForPost();
        myRef = database.getReference(currentUserUid + SEPARATOR + POST_DIRECTORY +
                SEPARATOR + generatedPostId + SEPARATOR + DATE_DIRECTORY);
        myRef.setValue(getCurrentDateTime());
        myRef = database.getReference(currentUserUid + SEPARATOR + POST_DIRECTORY +
                SEPARATOR + generatedPostId + SEPARATOR + TEXT_DIRECTORY);
        myRef.setValue("This is your first message");
    }

    private String getCurrentDateTime() {
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(currentDate);
    }

    private String generateIdForPost() {
        UUID uuid = UUID.randomUUID();
        return getString(R.string.postId) + uuid;
    }

    public void startListMessageActivity() {
        Intent intent = new Intent(RegistrationActivity.this, ListPostsActivity.class);
        startActivity(intent);
    }

    private String getEmail() {
        return editTextEmail.getText().toString();
    }

    private String getPassword() {
        return editTextPassword.getText().toString();
    }

    public String getSelectGender() {
        int selectedId = editTextRadioGroup.getCheckedRadioButtonId();
        RadioButton radioGender = (RadioButton) findViewById(selectedId);
        return radioGender.getText() + "";
    }

    private void initConstants() {
        POST_DIRECTORY = getResources().getString(R.string.post_directory);
        SEPARATOR = getString(R.string.separator);
        DATE_DIRECTORY = getString(R.string.date_directory);
        PROFILE_DIRECTORY = getString(R.string.profile_directory);
        TEXT_DIRECTORY = getString(R.string.text_directory);
    }
}
