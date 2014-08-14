package arkanoid.objects;

import arkanoid.main.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;

public class Ball extends Circle {
	
	private Texture texture;
	private int speed;
	private float radians;
	private boolean isOut;
	private float bounceLimit;
	private float speedTimer = 0f;
	private float dx = 0;
	private float ndx = 0;
	private float delay = 3f;
	
	public Ball() {
		super(Game.WIDTH/2 - 10/2, 300, 10);
		speed = 360;
		radians = MathUtils.random(225, 315) * MathUtils.degreesToRadians;
		isOut = false;		
		texture = new Texture(Gdx.files.internal("ball.png"));
		bounceLimit = 0.05f;
	}
	
	public void update(float dt) {
		// sprawdzenie limitu odbicia i aktualizacja
		if(bounceLimit > 0)
			bounceLimit -= dt;
		
		// sprawdzenie czy kulka odbija sie od krawedzi
		if(x <= radius || x >= Game.WIDTH - radius) {
			if(radians <= 3.1415)
				radians = 3.1415f - radians;
			else
				radians = 3*3.1415f - radians;
		}
		if(y >= Game.HEIGHT - radius) {
				radians = 2*3.1415f - radians;
		}
		
		// sprawdzenie czy gracz traci zycie
		if(y < 0) {
			isOut = true;
		}
		
		// sprawdzenie bonusu szybkosci
		if(speedTimer > 0)
			speedTimer -= dt;
		else {
			if(speed > 360) {
				speed = 360;
				texture.dispose();
				texture = new Texture(Gdx.files.internal("ball.png"));
			}
		}
		
		// sprawdzenie czy kulka jest przyklejona do podkladki
		if(speed == 0 && delay <= 0)
			x += dx - ndx;
		
		// sprawdzenie czy kulka ma pozostac w miejscu
		if(delay > 0) {
			speed = 0;
			delay -= dt;
			if(delay <= 0)
				speed = 360;
		}
		
		x += speed * dt * MathUtils.cos(radians);
		y += speed * dt * MathUtils.sin(radians);
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(texture, x-radius, y-radius);
	}
	
	public void handleInput() {
		if(speed == 0 && Gdx.input.isKeyPressed(Keys.SPACE))
			speed = 360;
	}
	
	public void reset() {
		x = Game.WIDTH/2 - 10/2;
		y = 300;
		radians = MathUtils.random(225, 315) * MathUtils.degreesToRadians;
		isOut = false;
		delay = 3f;
		texture.dispose();
		texture = new Texture(Gdx.files.internal("ball.png"));
	}
	
	public boolean isOut() { return isOut; }
	
	public boolean hitBlock(Block b) {
		if(!canHit())
			return false;
			
		boolean hor = ((x >= b.x) && (x <= b.x + Block.block_width)
				      || (x < b.x && (y <= x + b.y - b.x || y >= -x + b.y + Block.block_height + b.x))
				      || (x > b.x + Block.block_width && (y <= -x + b.y + Block.block_width + b.x || y >= x + b.y + Block.block_height - b.x - Block.block_width)));
				   
		if(Intersector.overlaps(this, b) && hor) {
			radians = 2*3.1415f - radians;
			bounceLimit = 0.05f;
			return true;
		}
		
		if(Intersector.overlaps(this, b)) {
			if(radians <= 3.1415)
				radians = 3.1415f - radians;
			else
				radians = 3*3.1415f - radians;
			bounceLimit = 0.05f;
			return true;
		}
		return false;
	}
	
	public void hitPad(Pad pad) {
		if(Intersector.overlaps(this, pad) && canHit()) {
			radians = 2*3.1415f - radians;
			if(pad.lastMove() == Pad.LEFT)
				radians += 0.05 * radians;
			else if(pad.lastMove() == Pad.RIGHT)
				radians -= 0.05 * radians;
			bounceLimit = 0.05f;
			if(pad.isGlued()) {
				y += 2;
				speed = 0;
				dx = Math.abs(pad.x - x);
			}
		}
		ndx = Math.abs(pad.x - x);
	}
	
	private boolean canHit() {
		return bounceLimit <= 0;
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public void speedUp() {
		if(speedTimer > 0)
			speedTimer += 15;
		else {
			speed += 180;
			speedTimer = 15;
			texture.dispose();
			texture = new Texture(Gdx.files.internal("speedball.png"));
		}
	}
}
