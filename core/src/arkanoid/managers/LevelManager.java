package arkanoid.managers;

import arkanoid.main.Game;
import arkanoid.objects.Block;

import com.badlogic.gdx.utils.Array;

public class LevelManager {
	
	private static Array<Array<Block>> levels;
	
	static {
		levels = new Array<Array<Block>>();
		
		Array<Block> currLevel = new Array<Block>();
		Block currBlock;
		int i = 0, j = 0;
		
		//level 0
		for(i=0; i<4; i++) {
			for(j=0; j<6; j++) {
				currBlock = new Block(Block.LOW);
				currLevel.add(currBlock);
				currBlock.x = 275 + j*100;
				currBlock.y = Game.HEIGHT - 100 - 50*i;
			}
		}
		levels.add(currLevel);
		
		//level 1
		currLevel = new Array<Block>();
		i = 0; j = 0;
		for(i=0; i<2; i++) {
			for(j=0; j<7; j++) {
				currBlock = new Block(Block.LOW);
				currLevel.add(currBlock);
				currBlock.x = 375 + j*50;
				currBlock.y = Game.HEIGHT - 100 - 6*15*i;
			}
		}
		for(i=0; i<5; i++) {
			for(j=0; j<2; j++) {
				currBlock = new Block(Block.LOW);
				currLevel.add(currBlock);
				currBlock.x = 375 + j*50*6;
				currBlock.y = Game.HEIGHT - 100 - 15 - 15*i;
			}
		}
		for(i=0; i<5; i++) {
			for(j=0; j<5; j++) {
				if((i+j)%2 == 0) {
					currBlock = new Block(Block.MED);
					currLevel.add(currBlock);
					currBlock.x = 375 + 50 + j*50;
					currBlock.y = Game.HEIGHT - 100 - 15 - 15*i;
				}
			}
		}
		levels.add(currLevel);
	}
	
	public static Array<Block> getLevel(int i) {
		return levels.get(i);
	}
	
	public static int size() {
		return levels.size;
	}

}
