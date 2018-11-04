package comdfsgfrgtdg.example.karpo.task.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.adapter.PostAdapter;
import comdfsgfrgtdg.example.karpo.task.model.Post;

public class ListPostsActivity extends AppCompatActivity {

    public static String POST_DIRECTORY;
    public static String PROFILE_DIRECTORY;
    private PostAdapter postAdapter;
    private FirebaseAuth mAuth;
    private GenericTypeIndicator<String> genericString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_posts_user);

        PROFILE_DIRECTORY = getResources().getString(R.string.profile_directory);
        POST_DIRECTORY = getResources().getString(R.string.post_directory);
        mAuth = FirebaseAuth.getInstance();

        initRecyclerView();
        if (mAuth != null) readFromDB();
    }

    private void readFromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Post> postsFromData = createPostsFromData(dataSnapshot);
                loadPosts(postsFromData);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListPostsActivity.this, getString(R.string.read_value_exception), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private GenericTypeIndicator<String> getGenericString(){
        if (genericString == null) {
            genericString = new GenericTypeIndicator<String>(){};
        }
        return genericString;
    }

    private String getCurrentUserUid(){
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }

    public List<Post> createPostsFromData(DataSnapshot dataSnapshot) {
        String uidCurrentUser = getCurrentUserUid();
        DataSnapshot child = dataSnapshot.child(uidCurrentUser).child(POST_DIRECTORY);
        Iterable<DataSnapshot> children = child.getChildren();
        genericString = getGenericString();
        ArrayList<Post> posts = new ArrayList<>();
        for (DataSnapshot item : children){
            String message = item.child(getString(R.string.text_directory)).getValue(genericString);
            String date = item.child(getString(R.string.date_directory)).getValue(genericString);
            Post post = new Post(message, convertStringToDate(date));
            posts.add(post);
        }
        return posts;
    }

    @SuppressLint("SimpleDateFormat")
    private Date convertStringToDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat();
        Date convertedDate;
        try {
            convertedDate = formatter.parse(date);
            return convertedDate;
        } catch (ParseException e) {
            Toast.makeText(ListPostsActivity.this, "Unable to convert date", Toast.LENGTH_SHORT).show();
            return new Date(18/12/2000);
        }
    }

    private void initRecyclerView() {
        RecyclerView postsRecyclerView = findViewById(R.id.posts_recycler_view);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        postAdapter = new PostAdapter();
        postsRecyclerView.setAdapter(postAdapter);
    }

    private void initToolbar() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void loadPosts(List<Post> postsFromData) {
        postAdapter.setItems(postsFromData);
    }

}
