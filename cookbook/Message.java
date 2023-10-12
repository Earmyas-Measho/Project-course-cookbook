/**
 * 
 */
package cookbook;

/**
 * Class Message
 *
 */
public class Message {

	private int id;
	private int senderId;
	private String content;
	private int receiverId;
	private int recipeId;
	
	
	// Default Constructor
	public Message() {
		
	}

	public Message(int senderId, String content, int receiverId, int recipeId) {
		super();
		this.senderId = senderId;
		this.content = content;
		this.receiverId = receiverId;
		this.recipeId = recipeId;
	}

	// Constructor
	public Message(int id, int senderId, String content, int receiverId, int recipeId) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.content = content;
		this.receiverId = receiverId;
		this.recipeId = recipeId;
	}

	// Getters
	public int getId() {
		return id;
	}

	public int getSenderId() {
		return senderId;
	}

	public String getContent() {
		return content;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public int getRecipeId() {
		return recipeId;
	}

	
	// Setters
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}
	
	public String toString() {
		return "The message from " + senderId + " to " + receiverId + " with this recipe " + receiverId + " has this information: \n" + content;
	}
	
	
	
}
