package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
//import entities.Crabby;
import main.Game;
import static utilz.HelpMethods.GetLevelData;
//import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	//private ArrayList<Crabby> crabs;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	 
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
	//	createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	
	// player spawn calculations
		private void calcPlayerSpawn() {
			playerSpawn = GetPlayerSpawn(img);
		}
		
		public Point getPlayerSpawn() {
			return playerSpawn;
		}
	// end of that	
	private void calcLvlOffsets() {
		// TODO Auto-generated method stub
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}
/*
	private void createEnemies() {
		// TODO Auto-generated method stub
		crabs = GetCrabs(img);
	}
	*/

	private void createLevelData() {
		// TODO Auto-generated method stub
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
/*
	public ArrayList<Crabby> getCrabs() {
		return crabs;
	}
*/
	
}
