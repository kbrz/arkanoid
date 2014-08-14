package arkanoid.states;

import arkanoid.main.Game;
import arkanoid.managers.BonusManager;
import arkanoid.managers.GameStateManager;
import arkanoid.managers.LevelManager;
import arkanoid.objects.Ball;
import arkanoid.objects.Block;
import arkanoid.objects.Bonus;
import arkanoid.objects.Bullet;
import arkanoid.objects.Pad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Array.ArrayIterator;

public class Play extends GameState {
	
	private Pad pad;
	private Ball ball;
	private Array<Block> blocks;
	private BonusManager bsm;
	private Array<Bonus> bonuses;
	private Array<Bullet> bullets;
	private BitmapFont bf;
	
	private int lives;
	private int score;
	private int level;
	private float fireCd;
	
	public Play(GameStateManager gsm) {
		super(gsm, GameState.PLAY);
	}

	@Override
	public void create() {
		pad = new Pad();
		ball = new Ball();
		bf = new BitmapFont();
		
		blocks = LevelManager.getLevel(0);
		
		bsm = new BonusManager(pad, ball);
		bonuses = new Array<Bonus>();
		bullets = new Array<Bullet>();
		
		lives = 3;
		score = 0;
		level = 0;
		fireCd = 1f;
	}

	@Override
	public void update(float dt) {
		ball.update(dt);
		pad.update(dt);
		ball.hitPad(pad);
		
		// sprawdzenie czy kulka zbija blok
		ArrayIterator<Block> biter = new ArrayIterator<Block>(blocks);
		Block curr;
		while(biter.hasNext()) {
			curr = biter.next();
			if(ball.hitBlock(curr)) {
				if(curr.type == Block.HI) {
					blocks.add(new Block(curr.x, curr.y, Block.MED));
					score += 100;
				}
				else if(curr.type == Block.MED) {
					blocks.add(new Block(curr.x, curr.y, Block.LOW));
					score += 50;
				}
				else
					score += 10;
				Bonus bonus = bsm.spawnRandomBonus(curr);
				if(bonus != null)
					bonuses.add(bonus);
				curr.dispose();
				biter.remove();
			}
		}
		
		// update'owanie bonusow
		ArrayIterator<Bonus> bsiter = new ArrayIterator<Bonus>(bonuses);
		Bonus bscurr;
		while(bsiter.hasNext()) {
			bscurr = bsiter.next();
			bscurr.update(dt);
			if(bscurr.y < -15) {
				bscurr.dispose();
				bsiter.remove();
			}
			if(bscurr.isObtained(pad)) {
				bscurr.dispose();
				if(pad.lastCatch() <= 0) {
					bsm.applyEffect(bscurr.type());
					bsiter.remove();
					pad.resetLastCatch();
				}
			}
		}
		
		// update'owanie cooldaownu pistoletu
		if(fireCd > 0)
			fireCd -= dt;
		
		// update'owanie pociskow
		ArrayIterator<Bullet> bliter = new ArrayIterator<Bullet>(bullets);
		Bullet blcurr;
		while(bliter.hasNext()) {
			blcurr = bliter.next();
			blcurr.update(dt);
			if(blcurr.y > Game.HEIGHT) {
				blcurr.dispose();
				bliter.remove();
			}
			biter.reset();
			while(biter.hasNext()) {
				curr = biter.next();
				if(blcurr.overlaps(curr)) {
					curr.dispose();
					blcurr.dispose();
					biter.remove();
					bliter.remove();
					break;
				}
			}
		}
		
		// sprawdzenie czy gracz traci zycie
		if(ball.isOut()) {
			lives--;
			ball.reset();
		}
		
		// sprawdzenie czy gra sie skonczyla
		if(lives <= 0) {
			Game.score = score;
			gsm.dispose();
			gsm.setState(new GameOver(gsm));
		}
		
		// sprawdzenie czy level zostal ukonczony i wczytanie nastepnego
		if(blocks.size <= 0) {
			level++;
			if(level >= LevelManager.size()) {
				Game.score = score;
				gsm.setState(new GameOver(gsm));
			}
			else {
				blocks = LevelManager.getLevel(level);
				ball.reset();
			}
		}
	}

	@Override
	public void draw(SpriteBatch sb) {
		sb.setProjectionMatrix(Game.cam.combined);
		sb.enableBlending();
		sb.begin();
		
		// rysowanie kulki i podkladki
		pad.draw(sb);
		ball.draw(sb);
		
		// rysowanie blokow
		for(Block b: blocks) {
			b.draw(sb);
		}
		
		// rysowanie bonusow
		for(Bonus b: bonuses) {
			b.draw(sb);
		}
		
		// rysowanie pociskow
		for(Bullet b: bullets) {
			b.draw(sb);
		}
		
		// wypisanie informacji dla gracza
		bf.draw(sb, "Lives: " + lives + "  |  Score: " + score, 920, 595);
		
		sb.end();
	}
	
	@Override
	public void handleInput() {
		pad.handleInput();
		ball.handleInput();
		
		if(pad.isArmed() && fireCd <= 0 && Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			bullets.add(new Bullet(pad.x+pad.width/2, pad.y+pad.height));
			fireCd = 1f;
		}
		
		if(Game.cheat)
			pad.x = ball.x - pad.width/2;
	}

	@Override
	public void dispose() {
		pad.dispose();
		ball.dispose();
		bf.dispose();
		
		ArrayIterator<Block> biter = new ArrayIterator<Block>(blocks);
		Block curr;
		while(biter.hasNext()) {
			curr = biter.next();
			curr.dispose();
			biter.remove();
		}
		
		ArrayIterator<Bonus> bsiter = new ArrayIterator<Bonus>(bonuses);
		Bonus bscurr;
		while(bsiter.hasNext()) {
			bscurr = bsiter.next();
			bscurr.dispose();
			bsiter.remove();
		}
		
		ArrayIterator<Bullet> bliter = new ArrayIterator<Bullet>(bullets);
		Bullet blcurr;
		while(bliter.hasNext()) {
			blcurr = bliter.next();
			blcurr.dispose();
			bliter.remove();
		}
	}
}
