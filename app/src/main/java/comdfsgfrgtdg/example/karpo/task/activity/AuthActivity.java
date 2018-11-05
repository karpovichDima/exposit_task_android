package comdfsgfrgtdg.example.karpo.task.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        String email = getEmail();
        String password = getPassword();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startListMessageActivity();
                } else {
                    Toast.makeText(AuthActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
}