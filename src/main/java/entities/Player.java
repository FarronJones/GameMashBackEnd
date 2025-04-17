	package entities;
	
	import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;

	import static utilz.Constants.*;
	import static utilz.Constants.PlayerConstants.FALLING;
	import static utilz.Constants.PlayerConstants.GetSpriteAmount;
	import static utilz.Constants.PlayerConstants.IDLE;
	import static utilz.Constants.PlayerConstants.JUMP;
	import static utilz.Constants.PlayerConstants.RUNNING;
	import static utilz.HelpMethods.CanMoveHere;
	import static utilz.HelpMethods.GetEntityXPosNextToWall;
	import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
	import static utilz.HelpMethods.IsEntityOnFloor;
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
	
	
		private boolean moving = false, attacking = false;
		private boolean left, right, up, down, jump;
		
		private int[][] lvlData;
		
		//Player(0)
		//dimensions for player(0) sprite hitbox = 21x4
		//Offset = How far away from (0,0) the top left of the hitbox is
	//	private float xDrawOffset = 21 * Game.SCALE; 
	//	private float yDrawOffset = 4 * Game.SCALE;
	//	
		//Burger
		//dimensions for offset of player sprite hitbox = 6x13
		
		private float xDrawOffset = 6 * Game.SCALE; 
		private float yDrawOffset = 13 * Game.SCALE;
		
		//Jumping / gravity
		private float airSpeed = 0f;
		private float jumpSpeed = -2.25f * Game.SCALE;
		private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
		
		
		
		private Playing playing;
		
		
		public Player(float x, float y, int width, int height, Playing playing) {
			super(x,y, width, height);
			this.playing = playing;
			this.state = IDLE;
			this.maxHealth = 100;
			this.currentHealth = maxHealth;
			this.walkSpeed = Game.SCALE * 1.0f;
			loadAnimations();
			
			//Player(0)
	//		initHitbox(20, 28); //Player(0) Sprite hitbox is 20x28;
			
			//Burger
			initHitbox(20, 18); //Burger Sprite hitbox is 20x18;
			
		}
		public void setSpawn(Point spawn) {
			this.x = spawn.x;
			this.y = spawn.y;
			hitbox.x = x;
			hitbox.y = y;
		}
		public void update() {
			if(currentHealth <=0) {
				playing.setGameOver(true);
			return;
			}
			
			updatePos();
			updateAnimationTick();
			setAnimation();
			
		}
	
	public void render(Graphics g, int lvlOffset) {
				
		g.drawImage(animations[state][aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset, (int)(hitbox.y - yDrawOffset), width, height, null);
		//z`drawHitbox(g);
	}
		
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
			}
				
		}
		
	}
	private void setAnimation() {
		
		int startAni = state;
		
		if(moving)
			state = RUNNING;
		else 
			state = IDLE;
		
		if(inAir) {
			if(airSpeed < 0)
				state = JUMP;
			else
				state = FALLING;
		}
				
//		if(attacking)
//			playerAction = ATTACK_1; 
		
		if(startAni != state)
			resetAniTick();
				
	}
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
		
	}
	private void updatePos() {
		
		moving = false;
		
		if(jump)
			jump();
		
//		if(!left && !right && !inAir)
//			return;
		
		if(!inAir)
			if((!left && !right) || (right && left))
				return;
		
		float xSpeed = 0;
			
		if(left) 
			xSpeed -= walkSpeed;
		if(right) 
			xSpeed += walkSpeed;
		
		if(!inAir) 
			if(!IsEntityOnFloor(hitbox, lvlData)) 
				inAir = true;
			
		if(inAir) {
			
			if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			}else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if(airSpeed > 0) 
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
			
		}else 
			updateXPos(xSpeed);
			
		moving = true;
	}
		
		
	private void jump() {
		if(inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;
		
	}
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
		
	}
	private void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
					
		}else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
					
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
		
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
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
	
	public void setJump(boolean jump) {
		this.jump = jump;
		
	}
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		moving = false;
		state = IDLE;
		currentHealth = maxHealth;
		
		hitbox.x = x;
		hitbox.y = y;
		
		if(!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
		
		
	}
	
	
	}

