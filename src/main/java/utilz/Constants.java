package utilz;

import main.Game;




public class Constants {
	
	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 15;
	
	public static class ObjectConstants {
		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;

		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;

		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case RED_POTION, BLUE_POTION:
				return 7;
			case BARREL, BOX:
				return 8;
			}
			return 1;
		}
	}
	public static class Environment{
	public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
	public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
	public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
	public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
	
		
	public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
	public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
	public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	
		
	}
	
	public static class Buttons{
		public static final int B_WIDTH_DEFAULT = 140;
		public static final int B_HEIGHT_DEFAULT = 56;
		public static final int B_WIDTH= (int) (B_WIDTH_DEFAULT * Game.SCALE);
		public static final int B_HEIGHT= (int) (B_HEIGHT_DEFAULT * Game.SCALE);
 
	}

	public static class PauseButtons{
		public static final int SOUND_SIZE_DEFAULT=42;
		public static final int SOUND_SIZE=(int)(SOUND_SIZE_DEFAULT*Game.SCALE); 
	}

	public static class URMButtons{
		public static final int URM_DEFAULT_SIZE=56;
		public static final int URM_SIZE=(int)(URM_DEFAULT_SIZE*Game.SCALE);
		
	}

	public static class VolumeButtons{
		public static final int VOLUME_DEFAULT_WIDTH=28;
		public static final int VOLUME_DEFAULT_HEIGHT=44;
		public static final int SLIDER_DEFAULT_WIDTH=215;

		public static final int VOLUME_WIDTH=(int)(VOLUME_DEFAULT_WIDTH*Game.SCALE);
		public static final int VOLUME_HEIGHT=(int)(VOLUME_DEFAULT_HEIGHT*Game.SCALE);
		public static final int SLIDER_WIDTH=(int)(SLIDER_DEFAULT_WIDTH*Game.SCALE);
	}

	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	public static class PlayerConstants{
		//Player actions 
		//Player(0)
//		public static final int IDLE = 0;
//		public static final int RUNNING  = 1;
//		public static final int JUMP = 2;
//		public static final int FALLING = 3;
//		public static final int GROUND = 4;
//		public static final int HIT = 5;
//		public static final int ATTACK_1 = 6;
//		public static final int ATTACK_JUMP_1 = 7;
//		public static final int ATTACK_JUMP_2 = 8;
//		
		//Burger
		public static final int IDLE = 1;
		public static final int RUNNING  = 2;
		public static final int JUMP = 3;
		public static final int FALLING = 4;
		
		public static int GetSpriteAmount(int player_action) {
			
			//Pirate
//			switch(player_action) {
//			
//			case RUNNING: 
//				return 6;
//			case IDLE: 
//				return 5;
//			case HIT: 
//				return 4;
//			case JUMP:
//			case ATTACK_1:
//			case ATTACK_JUMP_1:
//			case ATTACK_JUMP_2:
//				return 3;
//			case GROUND:
//				return 2;
//			case FALLING:
//			default:
//				return 1;
//			}
			
//			Burger
			switch(player_action) {
			
			case RUNNING: 
			case IDLE: 
				return 6;
			case JUMP:
			case FALLING:
			default:
				return 2;
			}
			
		}
	}
}//end class
		
	


