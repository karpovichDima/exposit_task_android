package comdfsgfrgtdg.example.karpo.task.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
