
import java.time.LocalDateTime;

public class Comment {
    private User author;
    private String text;
    private LocalDateTime timestamp;

    public Comment(User author, String content) {
        this.author = author;
        this.text = content;
        this.timestamp = LocalDateTime.now();
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}