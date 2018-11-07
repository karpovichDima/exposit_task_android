package comdfsgfrgtdg.example.karpo.task.data;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import comdfsgfrgtdg.example.karpo.task.R;
import comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter;
import comdfsgfrgtdg.example.karpo.task.presenter.PostPresenter;
import comdfsgfrgtdg.example.karpo.task.presenter.ProfilePresenter;
import comdfsgfrgtdg.example.karpo.task.presenter.RegistrationPresenter;
import comdfsgfrgtdg.example.karpo.task.view.AuthActivity;
import comdfsgfrgtdg.example.karpo.task.view.RegistrationActivity;

import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.DATE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.FAILED_REGISTRATION_STATUS;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.FAILED_SIGN_IN_STATUS;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.FIRST_MESSAGE;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.POST_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.PROFILE_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.SEPARATOR;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.SUCCESS_REGISTRATION_STATUS;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.SUCCESS_SIGN_IN_STATUS;
import static comdfsgfrgtdg.example.karpo.task.presenter.AuthPresenter.TEXT_DIRECTORY;
import static comdfsgfrgtdg.example.karpo.task.util.Timer.getCurrentDateTime;
import static comdfsgfrgtdg.example.karpo.task.util.Util.generateIdForPost;

public class FireBase {


    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private PostPresenter postPresenter;
    private ProfilePresenter profilePresenter;
    private AuthPresenter authPresenter;
    private RegistrationPresenter registrationPresenter;

    public FireBase(AuthPresenter authPresenter) {
        getFireBaseInstances();
        this.authPresenter = authPresenter;
    }

    public FireBase(PostPresenter postPresenter) {
        this.postPresenter = postPresenter;
        getFireBaseInstances();
    }

    public FireBase(ProfilePresenter profilePresenter) {
        this.profilePresenter = profilePresenter;
        getFireBaseInstances();
    }

    public FireBase(RegistrationPresenter registrationPresenter) {
        this.registrationPresenter = registrationPresenter;
        getFireBaseInstances();
    }

    public FireBase() {
        getFireBaseInstances();
    }

    private void getFireBaseInstances() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public String getCurrentUserUid() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
    }

    public String getCurrentUserEmail() {
        return Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();
    }

    public void setDataInPost(String path, String date) {
        DatabaseReference myRef = database.getReference(getCurrentUserUid() + path);
        myRef.setValue(date);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public void readDataPostsFromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postPresenter.postCreating(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void readDataProfileFromDB() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(getCurrentUserUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profilePresenter.loadUser(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void signIn(String email, String password, AuthActivity authActivity) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authPresenter.sendToast(SUCCESS_SIGN_IN_STATUS);
                    authPresenter.startListMessageActivity();
                } else {
                    authPresenter.sendToast(FAILED_SIGN_IN_STATUS);
                }
            }
        });
    }

    public void createNewFireBaseUser(final String email, final String password, RegistrationActivity registrationActivity) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registrationActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.signInWithEmailAndPassword(email, password);
                    String currentUserUid = getCurrentUserUid();
                    registrationPresenter.settingProfile(currentUserUid);
                    setFirstMessage(currentUserUid);
                    registrationPresenter.createToast(SUCCESS_REGISTRATION_STATUS);
                    registrationPresenter.startActivityCompletingRegistration();
                } else
                    registrationPresenter.createToast(FAILED_REGISTRATION_STATUS);
            }
        });
    }

    private void setFirstMessage(String currentUserUid) {
        String generatedPostId = generateIdForPost();
        myRef = database.getReference(currentUserUid + SEPARATOR + POST_DIRECTORY + SEPARATOR + generatedPostId + SEPARATOR + DATE_DIRECTORY);
        myRef.setValue(getCurrentDateTime());
        myRef = database.getReference(currentUserUid + SEPARATOR + POST_DIRECTORY +
                SEPARATOR + generatedPostId + SEPARATOR + TEXT_DIRECTORY);
        myRef.setValue(FIRST_MESSAGE);
    }

    public void setValue(String currentUserUid, List<String> fieldsValue, List<String> partOfLink) {
        Iterator<String> fieldIterator = fieldsValue.iterator();
        Iterator<String> linkIterator = partOfLink.iterator();
        while (fieldIterator.hasNext() && linkIterator.hasNext()) {
            String fieldValue = fieldIterator.next();
            String link = linkIterator.next();
            myRef = database.getReference(currentUserUid + SEPARATOR + PROFILE_DIRECTORY + SEPARATOR + link);
            myRef.setValue(fieldValue);
        }

    }
}

