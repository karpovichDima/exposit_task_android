package comdfsgfrgtdg.example.karpo.task.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.model.User;

public class ProfileActivity extends AppCompatActivity {

    public static String PROFILE_DIRECTORY;

    static boolean isTopOfTheStackProfile = false;
    private TextView name;
    private TextView lastName;
    private TextView email;
    private TextView gender;
    private TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        initComponentLayout();
        PROFILE_DIRECTORY = getResources().getString(R.string.profile_directory);
    }

    private void initComponentLayout() {
        name = (TextView) findViewById(R.id.user_name_text_view);
        lastName = (TextView) findViewById(R.id.user_last_name_text_view);
        email = (TextView) findViewById(R.id.user_email_text_view);
        gender = (TextView) findViewById(R.id.user_gender_text_view);
        age = (TextView) findViewById(R.id.user_age_text_view);
    }

    private void readFromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getCurrentUserUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = createUserFromData(dataSnapshot);
                loadUserToUI(user);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, getString(R.string.read_value_exception), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public User createUserFromData(DataSnapshot dataSnapshot) {
        String uidCurrentUser = getCurrentUserUid();
        DataSnapshot profileDirectory = dataSnapshot.child(uidCurrentUser).child(PROFILE_DIRECTORY);

        String lastName = (String) profileDirectory.child("lastName").getValue();
        String name = (String) profileDirectory.child("name").getValue();
        String gender = (String) profileDirectory.child("Gender").getValue();
        Long age = (Long) profileDirectory.child("Age").getValue();
        String email = getCurrentUserEmail();

        return new User(email, name, lastName, gender, convertLongToInt(age));
    }

    private int convertLongToInt(Long value) {
        return value.intValue();
    }

    private String getCurrentUserUid() {
        return Objects.requireNonNull(ListPostsActivity.mAuth.getCurrentUser()).getUid();
    }

    private String getCurrentUserEmail() {
        return Objects.requireNonNull(ListPostsActivity.mAuth.getCurrentUser()).getEmail();
    }

    private void loadUserToUI(User user) {
        name.setText(user.getName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
        age.setText(user.getAge() + "");
    }

    @Override
    public void onStart() {
        super.onStart();
        isTopOfTheStackProfile = true;
        readFromDB();
    }

    @Override
    public void onStop() {
        super.onStop();
        isTopOfTheStackProfile = false;
    }
}
