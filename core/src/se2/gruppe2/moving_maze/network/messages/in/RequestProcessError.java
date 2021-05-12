package se2.gruppe2.moving_maze.network.messages.in;

public class RequestProcessError {
    String category;
    String message;

    public RequestProcessError() {

    }

    public RequestProcessError(String category, String message) {
        this.category = category;
        this.message = message;
    }

    // GETTER & SETTER
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
