package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;
import utilz.Constants;
import utilz.Constants.ObjectConstants.*;

public class GameObject {
	protected int x, y, objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObject(int x, int y, int objType) {
		this.x = x;
		this.y = y;
		this.objType = objType;
	}
	protected void updateAnimationTick() {
		aniTick++;
		if(aniTick>= Constants.ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= Constants.ObjectConstants.GetSpriteAmount(objType)) {
				aniIndex = 0;
				if(objType == Constants.ObjectConstants.BARREL || objType ==Constants.ObjectConstants.BOX) {
					doAnimation = false;
					active = false;
				}
			}
		}
	}
	protected void initHitbox(int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, (int)(width *Game.SCALE), (int) (height *Game.SCALE));
	}
	public void reset() {
		aniIndex=0;
		aniTick=0;
		active = true;
		if(objType == Constants.ObjectConstants.BARREL || objType ==Constants.ObjectConstants.BOX)
			doAnimation=false;
			else
		    doAnimation=true;
	}
	public void drawHitbox(Graphics g) {
		//For debugging hitbox
		g.setColor(Color.pink);
		g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}
	public int getObjType() {
		return objType;
	}
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getxDrawOffSet() {
		return xDrawOffset;
	}

	public int getyDrawOffset() {
		return yDrawOffset;
	}
	public int getAniIndex() {
		return aniIndex;
	}
}
