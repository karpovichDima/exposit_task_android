package comdfsgfrgtdg.example.karpo.task.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import comdfsgfrgtdg.example.karpo.task.data.FireBase;
import comdfsgfrgtdg.example.karpo.task.model.Post;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.DATE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.POST_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.TEXT_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.util.Timer.convertStringToDate;
import static comdfsgfrgtdg.example.karpo.task.util.Util.getGenericString;

public class PostPresenter {

    private FireBase fireBasePresenterConnect;
    private PostView postView;

    public PostPresenter(PostView postView) {
        this.postView = postView;
        fireBasePresenterConnect = new FireBase(this);
    }

    private List<Post> createPostsFromData(DataSnapshot dataSnapshot) {
        String uidCurrentUser = fireBasePresenterConnect.getCurrentUserUid();
        DataSnapshot child = dataSnapshot.child(uidCurrentUser).child(POST_DIRECTORY);
        Iterable<DataSnapshot> children = child.getChildren();
        GenericTypeIndicator<String> genericString = getGenericString();
        ArrayList<Post> posts = new ArrayList<>();
        for (DataSnapshot item : children) {
            String message = item.child(TEXT_DIRECTORY).getValue(genericString);
            String date = item.child(DATE_DIRECTORY).getValue(genericString);
            Post post = new Post(message, convertStringToDate(date));
            posts.add(post);
        }
        return posts;
    }

    public void postCreating(DataSnapshot dataSnapshot) {
        List<Post> postsFromData = createPostsFromData(dataSnapshot);
        Collections.sort(postsFromData, Collections.<Post>reverseOrder());
        postView.loadPosts(postsFromData);
    }

    public void logout() {
        fireBasePresenterConnect.logout();
    }

    public void updatePosts() {
        fireBasePresenterConnect.readDataPostsFromDB();
    }

    public interface PostView {
        void loadPosts(List<Post> postsFromData);
    }
}


