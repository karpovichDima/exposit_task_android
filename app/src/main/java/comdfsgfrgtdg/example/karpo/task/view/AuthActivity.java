package comdfsgfrgtdg.example.karpo.task.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter;

public class AuthActivity extends AppCompatActivity implements AuthPresenter.AuthView{

    private EditText editTextEmail;
    private EditText editTextPassword;
    private AuthPresenter authPresenter;
    private List<String> fields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        initComponentLayout();
        authPresenter = new AuthPresenter(this, this);
        fields = new ArrayList<>();
    }

    private void initComponentLayout() {
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
    }

    public void startRegistrationActivity(View v) {
        Intent intent = new Intent(AuthActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void signIn(View v) {
        fields.add(getEmail());
        fields.add(getPassword());
        boolean isValidData = authPresenter.isValidFields(fields);
        if (!isValidData){
            Toast.makeText(AuthActivity.this, getString(R.string.text_login_exception), Toast.LENGTH_SHORT).show();
            fields.clear();
            return;
        }
        fields.clear();
        authPresenter.signIn(getEmail(), getPassword(), this);
    }

    private String getEmail() {
        return editTextEmail.getText().toString();
    }

    private String getPassword() {
        return editTextPassword.getText().toString();
    }

    @Override
    public void onBackPressed() {
        finish();
        this.finishAffinity();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(AuthActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void queryToStartListMassageActivity() {
        startListMessageActivity();
    }

    public void startListMessageActivity() {
        Intent intent = new Intent(AuthActivity.this, ListPostsActivity.class);
        startActivity(intent);
    }
}