package comdfsgfrgtdg.example.karpo.task.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = new ArrayList<>();

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        postViewHolder.bind(posts.get(i));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private TextView date;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.post_text_view);
            date = itemView.findViewById(R.id.post_date_text_view);
        }

        void bind(Post post) {
            message.setText(post.getMessage());
            date.setText(convertDateToString(post.getDate()));
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String convertDateToString(Date date) {
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat();
        return dateFormat.format(date);
    }

    public void setItems(Collection<Post> posts) {
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public void clearItems() {
        if (this.posts.size() != 0) {
            this.posts.clear();
            notifyDataSetChanged();
        }
    }

}
