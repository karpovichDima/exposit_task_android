package comdfsgfrgtdg.example.karpo.task.presenter;

import com.google.firebase.database.DataSnapshot;
import comdfsgfrgtdg.example.karpo.task.data.FireBase;
import comdfsgfrgtdg.example.karpo.task.model.User;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.AGE_HINT;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.GENDER;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.LAST_NAME;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.NAME;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.PROFILE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.util.Util.convertStringToInt;

public class ProfilePresenter {

    private FireBase fireBasePresenterConnect;
    private ProfileView profileView;

    public ProfilePresenter(ProfileView profileView) {
        fireBasePresenterConnect = new FireBase(this);
        this.profileView = profileView;
    }

    public void loadUser(DataSnapshot dataSnapshot){
        User user = createUserFromData(dataSnapshot);
        profileView.loadUserToUI(user);
    }

    private User createUserFromData(DataSnapshot dataSnapshot) {
        DataSnapshot profileDirectory = dataSnapshot.child(PROFILE_DIRECTORY);
        String lastName = (String) profileDirectory.child(LAST_NAME).getValue();
        String name = (String) profileDirectory.child(NAME).getValue();
        String gender = (String) profileDirectory.child(GENDER).getValue();
        String age = (String) profileDirectory.child(AGE_HINT).getValue();
        String email = fireBasePresenterConnect.getCurrentUserEmail();
        return new User(email, name, lastName, gender, convertStringToInt(age));
    }

    public void updateProfile() {
        fireBasePresenterConnect.readDataProfileFromDB();
    }

    public interface ProfileView {
        void loadUserToUI(User user);
    }




}
