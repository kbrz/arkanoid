package arkanoid.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
	
	private Texture texture;
	
	public Bullet(float x, float y) {
		super();
		this.x = x;
		this.y = y;
		
		texture = new Texture(Gdx.files.internal("bullet.png"));
		
		width = texture.getWidth();
		height = texture.getHeight();
	}
	
	public void update(float dt) {
		y += 400 * dt;
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(texture, x, y);
	}
	
	public void dispose() {
		texture.dispose();
	}

}
