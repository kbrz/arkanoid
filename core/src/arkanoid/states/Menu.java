package arkanoid.states;

import arkanoid.managers.GameStateManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu extends GameState {
	
	private BitmapFont bf;
	private int currSelected;
	
	public Menu(GameStateManager gsm) {
		super(gsm, GameState.MENU);
	}

	@Override
	public void create() {
		bf = new BitmapFont();
		currSelected = 0;
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void draw(SpriteBatch sb) {
		sb.begin();
		bfcolor(0);
		bf.draw(sb, "PLAY", 540, 300);
		bfcolor(1);
		bf.draw(sb, "EXIT", 538, 280);
		sb.end();
	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			if(currSelected < 1)
				currSelected++;
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			if(currSelected > 0)
				currSelected--;
		}
		
		if(Gdx.input.isKeyPressed(Keys.ENTER)) {
			if(currSelected == 0)
				gsm.setState(new Play(gsm));
			else if(currSelected == 1) {
				Gdx.app.exit();
			}
		}
	}
	
	private void bfcolor(int pos) {
		if(currSelected == pos)
			bf.setColor(1, 0, 0, 1);
		else
			bf.setColor(1, 1, 1, 1);
	}

	@Override
	public void dispose() {
		
	}

}
