package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
//	import static utilz.Constants.PlayerConstants.ATTACK_1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity{
	
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;

	private boolean moving = false, attacking = false;
	private boolean left, right, up, down;
	private float playerSpeed = 2.0f;
	
	public Player(float x, float y) {
		super(x,y);
		loadAnimations();
		
	}
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
		
		
	}
	
	public void render(Graphics g) {
		
		int scale = 8;
		int imgWidth = 32 * scale;
		int imgLength = 32 * scale;
		
		
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, imgWidth, imgLength, null);
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
			
		if(left && !right) {
			x -= playerSpeed;
			moving = true;
		}else if(right && !left) {
			x += playerSpeed;
			moving = true;
		}
		
		if(up && !down) {
			y -= playerSpeed;
			moving = true;
		} else if(down && !up) {
			y += playerSpeed;
			moving = true;
		}
		
	}
		
	
	
	private void loadAnimations() {
//		InputStream is=getClass().getResourceAsStream("/player_sprites(0).png");
//		try {
//			BufferedImage img=ImageIO.read(is);
//			animations= new BufferedImage[9][6];
//			for (int j=0; j<animations.length; j++)
//			for (int i=0; i<animations[j].length;i++)
//				animations[j][i]= img.getSubimage(i*64, j*40,64,40);
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//			
//		}
//		finally {
//			try {
//				is.close();//Frees up resources
//			}
//			catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		
		
		//For burger
		InputStream is=getClass().getResourceAsStream("/player_sprites.png");
		try {
			BufferedImage img=ImageIO.read(is);
			int spriteWidth = img.getWidth() / 6;  // 192 / 6 = 32
		    int spriteHeight = img.getHeight() / 5; // 160 / 5 = 32
	
		    animations = new BufferedImage[5][6];
	
		    for (int j = 0; j < 5; j++) { // Iterate over rows
		        for (int i = 0; i < 6; i++) { // Iterate over columns
		            animations[j][i] = img.getSubimage(i * spriteWidth, j * spriteHeight, spriteWidth, spriteHeight);
		        }
		    }
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
		finally {
			try {
				is.close();//Frees up resources
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}

	
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

