package arkanoid.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bonus extends Rectangle {
	
	public static int PAD = 563;
	public static int BALL = 564;
	public static int GLUE = 565;
	public static int GUN = 566;
	
	private Texture texture;
	private int type;
	private int speed = 300;
	
	public Bonus(int x, int y, int type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		
		if(type == PAD) {
			texture = new Texture(Gdx.files.internal("b_pad.png"));
		}
		else if(type == BALL) {
			texture = new Texture(Gdx.files.internal("b_ball.png"));
		}
		else if(type == GLUE) {
			texture = new Texture(Gdx.files.internal("b_glue.png"));
		}
		else if(type == GUN) {
			texture = new Texture(Gdx.files.internal("b_gun.png"));
		}
		
		width = texture.getWidth();
		height = texture.getHeight();
	}
	
	public void update(float dt) {
		y -= speed * dt;
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(texture, x, y);
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public boolean isObtained(Pad p) {
		if(y < p.height)
			if(x >= p.x && x <= p.x + p.width - width)
					return true;
		return false;
	}
	
	public int type() {
		return type;
	}
}
