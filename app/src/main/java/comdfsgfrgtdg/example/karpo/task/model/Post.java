package comdfsgfrgtdg.example.karpo.task.model;

import java.util.Date;

public class Post {

    private String message;
    private Date date;

    public Post(String message, Date date) {
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
}
