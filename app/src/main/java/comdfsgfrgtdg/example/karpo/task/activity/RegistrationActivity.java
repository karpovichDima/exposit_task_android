package comdfsgfrgtdg.example.karpo.task.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import comdfsgfrgtdg.example.karpo.task.R;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.input_email_reg);
        editTextPassword = (EditText) findViewById(R.id.input_password_reg);
    }

    public void registration(View v) {
        String email = getEmail();
        String password = getPassword();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    startListMessageActivity();
                } else
                    Toast.makeText(RegistrationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
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
}
