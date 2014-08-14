package arkanoid.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Block extends Rectangle {
	
	public static int LOW = 827;
	public static int MED = 828;
	public static int HI = 829;
	
	public static int block_width = 50;
	public static int block_height = 15;
	
	private Texture texture;
	public int type;
	
	public Block(int type) {
		super();
		this.type = type;
		
		if(type == LOW)
			texture = new Texture(Gdx.files.internal("blue.png"));
		else if(type == MED)
			texture = new Texture(Gdx.files.internal("green.png"));
		else if(type == HI)
			texture = new Texture(Gdx.files.internal("red.png"));
		
		width = block_width;
		height = block_height;
		
		x = 0; y = 0;
	}
	
	public Block(float x, float y, int type) {
		super();
		this.type = type;
		
		if(type == LOW)
			texture = new Texture(Gdx.files.internal("blue.png"));
		else if(type == MED)
			texture = new Texture(Gdx.files.internal("green.png"));
		else if(type == HI)
			texture = new Texture(Gdx.files.internal("red.png"));
		
		width = block_width;
		height = block_height;
		
		this.x = x; this.y = y;
	}
	
	public void draw(SpriteBatch sb) {
		sb.draw(texture, x, y);
	}
	
	public void dispose() {
		texture.dispose();
	}
}
