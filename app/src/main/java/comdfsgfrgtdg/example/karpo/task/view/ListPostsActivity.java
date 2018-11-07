package comdfsgfrgtdg.example.karpo.task.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;
import java.util.Objects;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.adapter.PostAdapter;
import comdfsgfrgtdg.example.karpo.task.model.Post;
import comdfsgfrgtdg.example.karpo.task.presenter.PostPresenter;

public class ListPostsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PostPresenter.PostView {

    private boolean isTopOfTheStackListPost = false;
    private PostAdapter postAdapter;
    private Toolbar toolbar;
    private PostPresenter postPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_posts_user);
        initToolbar();
        initNavigationDrawer(toolbar);
        initRecyclerView();
        postPresenter = new PostPresenter(this);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_profile:
                startNewActivity(this, ProfileActivity.class);
                break;
            case R.id.nav_home:
                finish();
                startNewActivity(this, ListPostsActivity.class);
                break;
            case R.id.nav_add_message:
                startNewActivity(this, AddPostActivity.class);
                break;
            case R.id.nav_logout:
                logout();
                startNewActivity(this, AuthActivity.class);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        postPresenter.logout();
    }

    private void startNewActivity(Activity currentActivity, Class openableClass) {
        Intent intent = new Intent(currentActivity, openableClass);
        startActivity(intent);
    }

    public void startAddPostActivity(View view) {
        startNewActivity(this, AddPostActivity.class);
    }

    @Override
    public void onStart() {
        super.onStart();
        postAdapter.clearItems();
        isTopOfTheStackListPost = true;
        postPresenter.updatePosts();
    }

    @Override
    public void onStop() {
        super.onStop();
        isTopOfTheStackListPost = false;
        postAdapter.clearItems();
    }

    @Override
    public void loadPosts(List<Post> postsFromData) {
        if (isTopOfTheStackListPost) postAdapter.setItems(postsFromData);
    }
}
