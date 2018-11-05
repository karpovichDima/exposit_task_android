package comdfsgfrgtdg.example.karpo.task.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import static android.app.PendingIntent.getActivity;

public class ListPostsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static String POST_DIRECTORY;
    static String SEPARATOR;
    static String DATE_DIRECTORY;
    static String TEXT_DIRECTORY;
    static FirebaseAuth mAuth;
    static boolean isTopOfTheStackListPost = false;
    private PostAdapter postAdapter;
    private Toolbar toolbar;
    private GenericTypeIndicator<String> genericString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_posts_user);

        initConstants();
        mAuth = FirebaseAuth.getInstance();
        initToolbar();
        initNavigationDrawer(toolbar);
        initRecyclerView();
    }

    private void initConstants() {
        POST_DIRECTORY = getResources().getString(R.string.post_directory);
        SEPARATOR = getString(R.string.separator);
        DATE_DIRECTORY = getString(R.string.date_directory);
        TEXT_DIRECTORY = getString(R.string.text_directory);
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
            String message = item.child(TEXT_DIRECTORY).getValue(genericString);
            String date = item.child(DATE_DIRECTORY).getValue(genericString);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_profile:
                startNewActivity(this, ProfileActivity.class);
                break;
            case R.id.nav_home:
                startNewActivity(this, ListPostsActivity.class);
                break;
            case R.id.nav_add_message:
                startNewActivity(this, AddPostActivity.class);
                break;
            case R.id.nav_logout:
                startNewActivity(this, AuthActivity.class);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startNewActivity(Activity currentActivity, Class openableClass) {
        Intent intent = new Intent(currentActivity, openableClass);
        startActivity(intent);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void initNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initRecyclerView() {
        RecyclerView postsRecyclerView = findViewById(R.id.posts_recycler_view);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter();
        postsRecyclerView.setAdapter(postAdapter);
    }

    private void loadPosts(List<Post> postsFromData) {
        if (isTopOfTheStackListPost) postAdapter.setItems(postsFromData);
    }

    @Override
    public void onStart() {
        super.onStart();
        postAdapter.clearItems();
        isTopOfTheStackListPost = true;
        readFromDB();
    }

    @Override
    public void onStop() {
        super.onStop();
        isTopOfTheStackListPost = false;
        postAdapter.clearItems();
    }
    
}
