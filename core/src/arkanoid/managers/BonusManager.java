package arkanoid.managers;

import arkanoid.objects.Ball;
import arkanoid.objects.Block;
import arkanoid.objects.Bonus;
import arkanoid.objects.Pad;

import com.badlogic.gdx.math.MathUtils;

public class BonusManager {
	
	private Pad pad;
	private Ball ball;
	
	public BonusManager(Pad p, Ball b) {
		pad = p;
		ball = b;
	}
	
	public Bonus spawnRandomBonus(Block b) {
		int chance = MathUtils.random(0, 100);
		if(chance < 3)
			return new Bonus((int) (b.x+b.width/2-15/2), (int) b.y - 15, Bonus.GUN);
		else if(chance < 6)
			return new Bonus((int) (b.x+b.width/2-15/2), (int) b.y - 15, Bonus.GLUE);
		else if(chance < 9)
			return new Bonus((int) (b.x+b.width/2-15/2), (int) b.y - 15, Bonus.BALL);
		else if(chance < 15)
			return new Bonus((int) (b.x+b.width/2-15/2), (int) b.y - 15, Bonus.PAD);
		else
			return null;
	}
	
	public void applyEffect(int type) {
		if(type == Bonus.PAD)
			pad.sizeUp();
		else if(type == Bonus.BALL)
			ball.speedUp();
		else if(type == Bonus.GLUE)
			pad.glued(true);
		else if(type == Bonus.GUN)
			pad.armed(true);
	}
}
