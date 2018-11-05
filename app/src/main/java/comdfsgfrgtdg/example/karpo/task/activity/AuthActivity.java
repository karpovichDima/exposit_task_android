package comdfsgfrgtdg.example.karpo.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import comdfsgfrgtdg.example.karpo.task.R;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
    }

    public void signIn(View v) {
        boolean fieldNotEmpty = isFieldNotEmpty();
        if (!fieldNotEmpty){
            Toast.makeText(AuthActivity.this, getString(R.string.text_login_exception), Toast.LENGTH_SHORT).show();
            return;
        }
        String email = getEmail();
        String password = getPassword();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, getString(R.string.success_hint), Toast.LENGTH_SHORT).show();
                    startListMessageActivity();
                } else {
                    Toast.makeText(AuthActivity.this, getString(R.string.failed_hint), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isFieldNotEmpty() {
        Editable emailText = editTextEmail.getText();
        Editable passwordText = editTextPassword.getText();
        if (emailText.length() < 1 || passwordText.length() < 1) return false;
        return true;
    }

    public void startRegistrationActivity(View v) {
        Intent intent = new Intent(AuthActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
    public void startListMessageActivity() {
        Intent intent = new Intent(AuthActivity.this, ListPostsActivity.class);
        startActivity(intent);
    }

    private String getEmail(){
        return editTextEmail.getText().toString();
    }
    private String getPassword(){
        return editTextPassword.getText().toString();
    }

    @Override
    public void onBackPressed() {
        finish();
        this.finishAffinity();
    }
}