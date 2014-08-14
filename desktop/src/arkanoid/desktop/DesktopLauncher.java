package arkanoid.desktop;

import arkanoid.main.Game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = Game.WIDTH; cfg.height = Game.HEIGHT;
		cfg.title = Game.TITLE;
		new LwjglApplication(new Game(), cfg);
	}
}
