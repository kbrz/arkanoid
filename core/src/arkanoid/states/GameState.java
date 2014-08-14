package arkanoid.states;

import arkanoid.managers.GameStateManager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {
	
	public static int PLAY = 13;
	public static int MENU = 14;
	public static int GAME_OVER = 15;
	public static int HIGHSCORES = 16;
	
	protected GameStateManager gsm;
	protected int type;
	
	public GameState(GameStateManager gsm, int type) {
		this.gsm = gsm;
		create();
	}
	
	public abstract void create();
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch sb);
	public abstract void handleInput();
	public abstract void dispose();

}
