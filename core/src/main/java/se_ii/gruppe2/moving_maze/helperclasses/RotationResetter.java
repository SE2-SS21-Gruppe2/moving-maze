package se_ii.gruppe2.moving_maze.helperclasses;

import se_ii.gruppe2.moving_maze.screens.GameScreen;

public class RotationResetter extends Thread {
    private int timeoutMillis;

    public RotationResetter(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeoutMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameScreen.tileJustRotated = false;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
}
