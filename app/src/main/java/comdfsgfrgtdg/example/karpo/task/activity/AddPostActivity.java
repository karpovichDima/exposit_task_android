package comdfsgfrgtdg.example.karpo.task.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comdfsgfrgtdg.example.karpo.task.R;

public class AddPostActivity extends AppCompatActivity {

    private EditText fieldInputMessage;
    private ImageView imageButton;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_add_message_activity);

        fieldInputMessage = (EditText) findViewById(R.id.field_message_text_view);
        imageButton = (ImageView) findViewById(R.id.imageView);
        database = FirebaseDatabase.getInstance();
    }

    public void sendMessage(View v) {
        // Write a message to the database
        myRef = database.getReference("cjdfzxmVfebEjvloAqcQqbBLMmF3/posts/post888/date");
        myRef.setValue("17.10.2016 15:36");
        myRef = database.getReference("cjdfzxmVfebEjvloAqcQqbBLMmF3/posts/post888/text");
        myRef.setValue("HELLOMTF");
    }




}
