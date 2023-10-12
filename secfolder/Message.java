package secfolder;

import cookbook.RecipeEntity;

public class Message {
    private User sender;
    private User receiver;
    private String content;
    private RecipeEntity recipe;

    public Message(User sender, User receiver, String content, RecipeEntity recipe) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.recipe = recipe;
    }

    public User getSender() {
        return this.sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return this.receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RecipeEntity getRecipe() {
        return this.recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }

}