package comdfsgfrgtdg.example.karpo.task.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.presenter.PostAddPresenter;

public class AddPostActivity extends AppCompatActivity implements PostAddPresenter.PostAddView {

    private EditText fieldInputMessage;
    private PostAddPresenter postAddPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_add_message_activity);
        initLayoutComponent();
        postAddPresenter = new PostAddPresenter(this);
    }

    private void initLayoutComponent() {
        fieldInputMessage = (EditText) findViewById(R.id.field_message_text_view);
        ImageView imageButton = (ImageView) findViewById(R.id.imageButton);
    }

    public void sendMessage(View v) {
        postAddPresenter.send(fieldInputMessage.getText(), this);
        finish();
    }



}
