package comdfsgfrgtdg.example.karpo.task.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import comdfsgfrgtdg.example.karpo.task.R;

import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.DATE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.POST_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.SEPARATOR;
import static comdfsgfrgtdg.example.karpo.task.activity.ListPostsActivity.TEXT_DIRECTORY;

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
        imageButton = (ImageView) findViewById(R.id.imageButton);
        database = FirebaseDatabase.getInstance();
    }



    public void sendMessage(View v) {
        String currentUserUid = getCurrentUserUid();
        String generatedPostId = generateIdForPost();
        myRef = database.getReference(currentUserUid + SEPARATOR + POST_DIRECTORY +
                                      SEPARATOR + generatedPostId + SEPARATOR + DATE_DIRECTORY);
        myRef.setValue(getCurrentDateTime());
        myRef = database.getReference(currentUserUid + SEPARATOR + POST_DIRECTORY +
                                      SEPARATOR + generatedPostId + SEPARATOR + TEXT_DIRECTORY);
        myRef.setValue(fieldInputMessage.getText() + "");
        finish();
    }

    private String getCurrentDateTime(){
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(currentDate);
    }

    private String generateIdForPost(){
        UUID uuid = UUID.randomUUID();
        return getString(R.string.postId) + uuid;
    }

    private String getCurrentUserUid(){
        return Objects.requireNonNull(ListPostsActivity.mAuth.getCurrentUser()).getUid();
    }



}
