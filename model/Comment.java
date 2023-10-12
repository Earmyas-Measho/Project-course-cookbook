package model;

import java.time.LocalDateTime;

public class Comment {
    private int id;
    private user author;
    private LocalDateTime timestamp;
    private String text;

    public Comment() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public user getAuthor() {
        return this.author;
    }

    public void setAuthor(user author) {
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}