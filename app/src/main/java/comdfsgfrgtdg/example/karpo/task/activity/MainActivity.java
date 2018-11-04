package comdfsgfrgtdg.example.karpo.task.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comdfsgfrgtdg.example.karpo.task.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, ListPostsActivity.class);
        startActivity(intent);
    }
    public void onClickAuth(View v) {
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);
    }
}
