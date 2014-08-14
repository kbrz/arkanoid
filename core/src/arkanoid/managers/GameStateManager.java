package arkanoid.managers;

import arkanoid.main.Game;
import arkanoid.states.GameState;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
	
	public Game game;
	private GameState currentState;
	
	public GameStateManager(Game game) {
		this.game = game;
	}
	
	public void update(float dt) {
		currentState.update(dt);
	}
	
	public void handleInput() {
		currentState.handleInput();
	}
	
	public void draw(SpriteBatch sb) {
		currentState.draw(sb);
	}
	
	public void dispose() {
		currentState.dispose();
	}
	
	public void setState(GameState state) { 
		currentState = state; 
	}

}
