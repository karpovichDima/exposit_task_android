package comdfsgfrgtdg.example.karpo.task.model;

import java.util.Date;

public class Post {

    private User user;
    private String message;
    private Date date;

    public Post(User user, String message, Date date) {
        this.user = user;
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
