package se2.gruppe2.moving_maze.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import se2.gruppe2.moving_maze.MovingMazeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 600;
		config.width = 1100;
		new LwjglApplication(MovingMazeGame.getGameInstance(), config);
	}
}
