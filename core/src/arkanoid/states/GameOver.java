package arkanoid.states;

import arkanoid.main.Game;
import arkanoid.managers.GameStateManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends GameState {
	
	private BitmapFont bf;
	
	public GameOver(GameStateManager gsm) {
		super(gsm, GameState.GAME_OVER);
	}

	@Override
	public void create() {
		bf = new BitmapFont();
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public void draw(SpriteBatch sb) {
		sb.begin();
		bf.draw(sb, "GAME OVER", 515, 400);
		bf.draw(sb, "Your score is " + Game.score, 500, 200);
		sb.end();
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			dispose();
		}
	}

	@Override
	public void dispose() {
		bf.dispose();
		Gdx.app.exit();
	}

}
