package comdfsgfrgtdg.example.karpo.task.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.model.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{

    private static final String BIG_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZ yyyy";
    private static final String MONTH_DAY_DATE_FORMATE = "MMM d";
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

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.post_text_view);
            date = itemView.findViewById(R.id.post_date_text_view);
        }

        public void bind(Post post){
            message.setText(post.getMessage());
            date.setText(post.getDate() + "");
        }
    }

    public void setItems(Collection<Post> posts){
        this.posts.addAll(posts);
        notifyDataSetChanged();
    }

    public void clearItems(){
        this.posts.clear();
        notifyDataSetChanged();
    }
}
