package comdfsgfrgtdg.example.karpo.task.presenter;

import android.content.Context;
import android.text.Editable;
import android.widget.Toast;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.data.FireBase;
import comdfsgfrgtdg.example.karpo.task.view.AddPostActivity;

import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.DATE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.POST_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.SEPARATOR;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.TEXT_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.util.Timer.getCurrentDateTime;
import static comdfsgfrgtdg.example.karpo.task.util.Util.generateIdForPost;
import static comdfsgfrgtdg.example.karpo.task.util.Validator.isLessThanHundred;

public class PostAddPresenter {

    private FireBase fireBasePresenterConnect;
    private AddPostActivity addPostActivity;

    public PostAddPresenter(AddPostActivity addPostActivity){
        this.addPostActivity = addPostActivity;
        fireBasePresenterConnect = new FireBase();
    }

    public void send(Editable text, Context context) {
        boolean lessThanHundred = isLessThanHundred(text);
        if (!lessThanHundred) {
            Toast.makeText(context, context.getString(R.string.text_very_big_exception), Toast.LENGTH_LONG).show();
            return;
        }
        String generatedPostId = generateIdForPost();
        setDate(generatedPostId);
        setText(generatedPostId, text.toString());
    }

    private void setText(String generatedPostId, String text) {
        String path = SEPARATOR + POST_DIRECTORY + SEPARATOR + generatedPostId + SEPARATOR + TEXT_DIRECTORY;
        fireBasePresenterConnect.setDataInPost(path, text);
    }

    private void setDate(String generatedPostId) {
        String path = SEPARATOR + POST_DIRECTORY + SEPARATOR + generatedPostId + SEPARATOR + DATE_DIRECTORY;
        fireBasePresenterConnect.setDataInPost(path, getCurrentDateTime());
    }


    public interface PostAddView{

    }
}
