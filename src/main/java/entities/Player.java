package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
//	import static utilz.Constants.PlayerConstants.ATTACK_1;

import static utilz.HelpMethods.CanMoveHere;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

/*
 * In order to change sprite you have to edit a few methods in these classes
 * Player (player class and loadAnimations class)
 * Constants (PlayerConstants and GetSpriteAmount)
 * Game (initClasses)
 * LoadSave (constants)
 * 
 * Easy way to uncomment on Eclipse
 * Ctrl + /
 * 
 * or 
 * 
 * Source + toggle comment
 * 
 */

public class Player extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;

	private boolean moving = false, attacking = false;
	private boolean left, right, up, down;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	
	//Player(0)
	//dimensions for player(0) sprite hitbox = 21x4
	//Offset = How far away from (0,0) the top left of the hitbox is
//	private float xDrawOffset = 21 * Game.SCALE; 
//	private float yDrawOffset = 4 * Game.SCALE;
	
	//Burger
	//dimensions for offset of player sprite hitbox = 6x13
	
	private float xDrawOffset = 6 * Game.SCALE; 
	private float yDrawOffset = 13 * Game.SCALE;

	
	public Player(float x, float y, int width, int height) {
		super(x,y, width, height);
		loadAnimations();
		
		//Player(0)
//		initHitbox(x, y, 20*Game.SCALE, 28*Game.SCALE); //Player(0) Sprite hitbox is 20x28;
		
		//Burger
		initHitbox(x, y, 20*Game.SCALE, 18*Game.SCALE); //Burger Sprite hitbox is 20x18;
		
	}
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
		
		
	}
	
	public void render(Graphics g) {
				
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset), (int)(hitbox.y - yDrawOffset), width, height, null);
		drawHitbox(g);
	}
		
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
				
		}
		
	}
	private void setAnimation() {
		
		int startAni = playerAction;
		
		if(moving)
			playerAction = RUNNING;
		else 
			playerAction = IDLE;
				
//		if(attacking)
//			playerAction = ATTACK_1; 
		
		if(startAni != playerAction)
			resetAniTick();
				
	}
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}
	private void updatePos() {
		
		moving = false;
		if(!left && !right && !up && !down)
			return;
		
		float xSpeed = 0, ySpeed = 0;
			
		if(left && !right) 
			xSpeed -= playerSpeed;
		else if(right && !left) 
			xSpeed += playerSpeed;
		
		
		if(up && !down) 
			ySpeed -= playerSpeed;
		else if(down && !up) 
			ySpeed += playerSpeed;

//		if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, lvlData)) {
//			this.x += xSpeed;
//			this.y += ySpeed;
//			moving = true;
//		}
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			hitbox.y += ySpeed;
			moving = true;
		}
	}
		
	
	
	private void loadAnimations() {
//		For player(0)
//		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
//
//		animations = new BufferedImage[9][6];
//		for (int j = 0; j < animations.length; j++)
//			for (int i = 0; i < animations[j].length; i++)
//				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
//		
//		}
	
		
		
		
			//For burger
			BufferedImage img=LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			int spriteWidth = img.getWidth() / 6;  // 192 / 6 = 32
		    int spriteHeight = img.getHeight() / 5; // 160 / 5 = 32
	
		    animations = new BufferedImage[5][6];
	
		    for (int j = 0; j < 5; j++) { // Iterate over rows
		        for (int i = 0; i < 6; i++) { // Iterate over columns
		            animations[j][i] = img.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight);
		        }
		    }
		}
	
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		
	}
	
	public void resetDirBooleans() {
		
		left = false;
		right = false;
		down = false;
		up = false;
	}
	
	public void setAttacking(boolean attacking) {
		
		this.attacking = attacking;
	}
	
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public boolean isRight() {
		return right;
	}
	public void setRight(boolean right) {
		this.right = right;
	}
	public boolean isUp() {
		return up;
	}
	public void setUp(boolean up) {
		this.up = up;
	}
	public boolean isDown() {
		return down;
	}
	public void setDown(boolean down) {
		this.down = down;
	}
	
	
	
	}

