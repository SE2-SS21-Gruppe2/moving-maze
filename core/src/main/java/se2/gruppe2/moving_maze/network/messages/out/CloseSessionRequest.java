package se2.gruppe2.moving_maze.network.messages.out;

public class CloseSessionRequest {
    String sessionKey;

    public CloseSessionRequest(){}

    public CloseSessionRequest(String sessionKey){
        this.sessionKey = sessionKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
