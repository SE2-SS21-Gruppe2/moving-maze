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
}
