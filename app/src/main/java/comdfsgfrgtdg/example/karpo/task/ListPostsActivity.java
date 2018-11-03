package comdfsgfrgtdg.example.karpo.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import comdfsgfrgtdg.example.karpo.task.adapter.PostAdapter;
import comdfsgfrgtdg.example.karpo.task.model.Post;

public class ListPostsActivity extends AppCompatActivity {

    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_posts_activity);

        initRecyclerView();
        loadPosts();
    }

    private void initRecyclerView() {
        postsRecyclerView = findViewById(R.id.posts_recycler_view);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        postAdapter = new PostAdapter();
        postsRecyclerView.setAdapter(postAdapter);
    }

    private Collection getTweets() {
        return Arrays.asList(
                new Post(null, "1", new Date(17 / 04 / 2018)),
                new Post(null, "2", new Date(17 / 04 / 2018)),
                new Post(null, "3", new Date(17 / 04 / 2018)),
                new Post(null, "4", new Date(17 / 04 / 2018)),
                new Post(null, "5", new Date(17 / 04 / 2018)),
                new Post(null, "6", new Date(17 / 04 / 2018)),
                new Post(null, "7", new Date(17 / 04 / 2018)),
                new Post(null, "8", new Date(17 / 04 / 2018)),
                new Post(null, "9", new Date(17 / 04 / 2018)),
                new Post(null, "10", new Date(17 / 04 / 2018)));
    }

    private void loadPosts(){
        Collection posts = getTweets();
        postAdapter.setItems(posts);
    }
}
