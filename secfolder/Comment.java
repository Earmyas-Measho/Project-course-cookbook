package secfolder;

import java.time.LocalDateTime;

public class Comment {
    private String author;
    private String content;
    private String recipe;
    //private LocalDateTime timestamp;

    public Comment(String author, String recipe, String content) {
        this.author = author;
        this.content = content;
        this.recipe = recipe;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String text) {
        this.content = content;
    }

    public String getRecipe() {
        return this.recipe;
    }

    /* 
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
    

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    */
}