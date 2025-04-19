package utilz;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utilz.Constants.ObjectConstants.*;

import main.Game;
import objects.GameContainer;
import objects.Potion;

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
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth) {
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
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		
		if(airSpeed > 0) {
			//falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		}else {
			//jumping
			return currentTile * Game.TILES_SIZE;
		}
		
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		//Check the pixel below bottomLeft and bottomRight
		if(!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1 , lvlData))
			if(!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		
		return true;
		
	}
	public static int[][] GetLevelData(BufferedImage img) {
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;
	}
		public static Point GetPlayerSpawn(BufferedImage img) {
			for (int j = 0; j < img.getHeight(); j++)
				for (int i = 0; i < img.getWidth(); i++) {
					Color color = new Color(img.getRGB(i, j));
					int value = color.getGreen();
					if (value == 100)
						return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				}
			return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
		}
	
	/*
	public static ArrayList<Crabby> GetCrabs(BufferedImage img) {
		ArrayList<Crabby> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == CRABBY)
					list.add(new Crabby(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;
	}*/


	
		public static ArrayList<Potion> GetPotions(BufferedImage img) {
			ArrayList<Potion> list = new ArrayList<>();
			for (int j = 0; j < img.getHeight(); j++)
				for (int i = 0; i < img.getWidth(); i++) {
					Color color = new Color(img.getRGB(i, j));
					int value = color.getBlue();
					if (value == RED_POTION || value == BLUE_POTION)
						list.add(new Potion(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
					
				}
			return list;
		}
		
		
		public static ArrayList<GameContainer> GetContainers(BufferedImage img) {
			ArrayList<GameContainer> list = new ArrayList<>();
			for (int j = 0; j < img.getHeight(); j++)
				for (int i = 0; i < img.getWidth(); i++) {
					Color color = new Color(img.getRGB(i, j));
					int value = color.getBlue();
					if (value == BOX || value == BARREL)
						list.add(new GameContainer(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
					
				}
			return list;
		}
	
	}//end class
	
	

