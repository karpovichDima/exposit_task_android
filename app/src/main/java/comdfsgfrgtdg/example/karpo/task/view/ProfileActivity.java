package comdfsgfrgtdg.example.karpo.task.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.model.User;
import comdfsgfrgtdg.example.karpo.task.presenter.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity implements ProfilePresenter.ProfileView {

    private boolean isTopOfTheStackProfile = false;
    private TextView name;
    private TextView lastName;
    private TextView email;
    private TextView gender;
    private TextView age;
    private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        initComponentLayout();
        profilePresenter = new ProfilePresenter(this);
    }

    private void initComponentLayout() {
        name = (TextView) findViewById(R.id.user_name_text_view);
        lastName = (TextView) findViewById(R.id.user_last_name_text_view);
        email = (TextView) findViewById(R.id.user_email_text_view);
        gender = (TextView) findViewById(R.id.user_gender_text_view);
        age = (TextView) findViewById(R.id.user_age_text_view);
    }

    public void loadUserToUI(User user) {
        name.setText(user.getName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());
        gender.setText(user.getGender());
        age.setText(user.getAge()+"");
    }

    @Override
    public void onStart() {
        super.onStart();
        isTopOfTheStackProfile = true;
        updateProfile();
    }

    private void updateProfile() {
        profilePresenter.updateProfile();
    }

    @Override
    public void onStop() {
        super.onStop();
        isTopOfTheStackProfile = false;
    }
}
