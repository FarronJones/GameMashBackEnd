package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public abstract class Entity {

	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private int playerDir = -1;
	private boolean moving = false;
	protected float x,y;
	
	public Entity(float x, float y) {
		this.x=x;
		this.y=y;
				
	}
	
	public void update() {
		updateAnimationTick();
		setAnimation();
		updatePos();
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 256, 160, null);
	}
	
	private void loadAnimations() {
		InputStream is = getClass().getResourceAsStream("/player_sprites.png"); //Imports image
		
		try {
			BufferedImage img = ImageIO.read(is);
			
			animations = new BufferedImage[9][6];
			
			for(int j = 0; j < animations.length; j++)
				for (int i = 0; i < animations[j].length; i++) {
					animations[j][i] = img.getSubimage(i*64, j*40, 64, 40);
				}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close(); //Frees up resources
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	public void setDirection(int direction) {
				
				this.playerDir = direction;
				moving = true;
				
			}
			
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	private void updateAnimationTick() {
		
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction))
				aniIndex = 0;
		}
		
	}
	private void setAnimation() {
		
		if(moving)
			playerAction = RUNNING;
		else 
			playerAction = IDLE;
				
				
	}
	private void updatePos() {
		
		if(moving) {
			switch(playerDir) {
			case LEFT:
				x -= 5;
				break;
			case UP:
				y -= 5;
				break;
			case RIGHT:
				x += 5;
				break;
			case DOWN:
				y += 5;
				break;
			
			}
		}
		
	}
	
		
		
}

