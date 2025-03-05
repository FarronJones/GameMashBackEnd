package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		
		if (!IsSolid(x,y,lvlData)) {
			if(!IsSolid(x + width, y + height,lvlData))
				if(!IsSolid(x + width, y,lvlData))
					if(!IsSolid(x, y + height,lvlData))
						return true;
			
		}
		return false;
	}
	
	//Uses level_one_data
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH) {
			return true;
		}
		if (y < 0 || y >= Game.GAME_WIDTH) {
			return true;
		}
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = lvlData[(int) yIndex][(int) xIndex];
		
		//Tile 11 is transparent so it should return false
		if (value >= 48 || value < 0 || value != 11) {
			return true;
		}else
			return false;
			
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		
		if(xSpeed > 0) {
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		}else {
			//Left
			return currentTile * Game.TILES_SIZE;
		}
		
	} 

}
