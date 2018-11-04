package comdfsgfrgtdg.example.karpo.task.activity;

import android.annotation.SuppressLint;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.adapter.PostAdapter;
import comdfsgfrgtdg.example.karpo.task.model.Post;

public class ListPostsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String POST_DIRECTORY;
    public static String PROFILE_DIRECTORY;
    private PostAdapter postAdapter;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private GenericTypeIndicator<String> genericString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_posts_user);

        PROFILE_DIRECTORY = getResources().getString(R.string.profile_directory);
        POST_DIRECTORY = getResources().getString(R.string.post_directory);
        mAuth = FirebaseAuth.getInstance();
        initToolbar();
        initNavigationDrawer(toolbar);
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
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_profile:
                break;
            case R.id.nav_home:
                break;
            case R.id.nav_add_message:
                break;
            case R.id.nav_logout:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        postAdapter.setItems(postsFromData);
    }
}
