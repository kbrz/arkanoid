package arkanoid.objects;

import arkanoid.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Pad extends Rectangle {
	
	public static int pad_width = 80;
	public static int pad_height = 20;
	
	public static int NO_MOVE = 977;
	public static int LEFT = 978;
	public static int RIGHT = 979;
	
	private Texture texture;
	private int speed;
	private int lastMove;
	private float sizeTimer;
	private float glueTimer;
	private float gunTimer;
	private float lastCatch;
	private boolean glued;
	private boolean armed;
	
	public Pad() {
		super();
		
		this.x = Game.WIDTH/2 - Pad.pad_width/2;
		this.y = 0;
		
		texture = new Texture(Gdx.files.internal("pad.png"));
		
		this.width = texture.getWidth();
		this.height = texture.getHeight();
		
		lastMove = NO_MOVE;
		speed = 480;
		
		sizeTimer = 0f;
		lastCatch = 0.05f;
		glued = false;
		glueTimer = 0f;
		armed = false;
		gunTimer = 0f;
	}
	
	public void update(float dt) {
		if(sizeTimer < 0) {
			if(width >= 160)
				texture.dispose();
				texture = new Texture(Gdx.files.internal("pad.png"));
				width = texture.getWidth();
		}
		else
			sizeTimer -= dt;
		
		if(glueTimer < 0)
			glued(false);
		else
			glueTimer -= dt;
		
		if(gunTimer < 0)
			armed = false;
		else
			gunTimer -= dt;
		
		if(lastCatch > 0)
			lastCatch -= dt;
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(texture, x, y);
	}
	
	public void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			moveLeft(Gdx.graphics.getDeltaTime());
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			moveRight(Gdx.graphics.getDeltaTime());
		}
		else
			lastMove = NO_MOVE;
	}
	
	private void moveLeft(float dt) {
		if(x > 0) {
			x -= speed * dt;
			lastMove = LEFT;
		}
	}
	
	private void moveRight(float dt) {
		if(x < Game.WIDTH - width) {
			x += speed * dt;
			lastMove = RIGHT;
		}
	}
	
	public int lastMove() { return lastMove; }
	
	public void dispose() {
		texture.dispose();
	}
	
	public void sizeUp() {
		if(width >= 160) {
			sizeTimer += 30;
			return; 
		}
		texture.dispose();
		texture = new Texture(Gdx.files.internal("pad_doubled.png"));
		sizeTimer = 30;
		width = texture.getWidth();
	}
	
	public void glued(boolean a) {
		if(glued)
			glueTimer += 15f;
		else {
			glued = a;
			if(a)
				glueTimer = 15f;
		}
	}
	
	public boolean isGlued() {
		return glued;
	}
	
	public void armed(boolean b) {
		if(armed)
			gunTimer += 15f;
		else {
			armed = b;
			if(b)
				gunTimer = 15f;
		}
	}
	
	public boolean isArmed() {
		return armed;
	}
	
	public float lastCatch() {
		return lastCatch;
	}
	
	public void resetLastCatch() {
		lastCatch = 0.05f;
	}
}
