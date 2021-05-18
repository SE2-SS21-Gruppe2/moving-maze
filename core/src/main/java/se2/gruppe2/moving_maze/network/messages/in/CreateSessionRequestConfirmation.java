package se2.gruppe2.moving_maze.network.messages.in;

public class CreateSessionRequestConfirmation {
    String sessionKey;

    public CreateSessionRequestConfirmation(){}

    public CreateSessionRequestConfirmation(String sessionKey){
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
