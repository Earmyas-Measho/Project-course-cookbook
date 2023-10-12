package model;

public class Message {
    private user sender;
    private user receiver;
    private String content;
    private Recipe recipe;

    public Message(user sender, user receiver, String content, Recipe recipe) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.recipe = recipe;
    }

    public user getSender() {
        return this.sender;
    }

    public void setSender(user sender) {
        this.sender = sender;
    }

    public user getReceiver() {
        return this.receiver;
    }

    public void setReceiver(user receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}