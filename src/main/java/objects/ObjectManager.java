package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import entities.Player;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
public class ObjectManager {
	private Playing playing;
	private BufferedImage[][] potionImgs, containerImgs;
	private BufferedImage spikeImg;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Spike> spikes;
	
		public ObjectManager(Playing playing) {
			this.playing = playing;
			loadImgs();
			
			
		}

		public void checkSpikesTouched(Player p){
			for(Spike s : spikes)
				if(s.getHitbox().intersects(p.getHitbox()))
					p.kill();
		}
		
		public void checkObjectTouched(Rectangle2D.Float hitbox) {
			
			for(Potion p: potions) {
				if(p.isActive()) {
					if(hitbox.intersects(p.getHitbox())) {
						p.setActive(false);
						//applyEffectToPlayer(p);
						
					}
					
				}
				
			//For completing level
			boolean allObtained = true;
			
			for (Potion p1: potions) {
				if(p1.isActive()) {
					allObtained = false;
					break;
				}
			}
			
			if (allObtained) {
				playing.setLevelCompleted(true);
				System.out.println("Level completed!");
			}
			
			}
			
		}
		
		public void applyEffectToPlayer(Potion p) {
			if(p.getObjType() == RED_POTION) {
				//playing.getPlayer().changeHealth(RED_POTION_VALUE);
			}
			else
				playing.getPlayer().changePower(BLUE_POTION_VALUE);
			
		}
		public void checkObjectHit(Rectangle2D.Float attackbox) {
			
			for(GameContainer gc : containers)
				if(gc.isActive()&& !gc.doAnimation) {
					if(gc.getHitbox().intersects(attackbox)) {
						gc.setAnimation(true);
						
						//Makes it so potions are dropped if box/barrel is destroyed
						int type = 0;
						if(gc.getObjType() == BOX)
							type = 1;
						potions.add(new Potion((int)(gc.getHitbox().x + gc.getHitbox().width /2),
								(int)(gc.getHitbox().y - gc.getHitbox().height/2), type));
						return;
					}
				}
		}
		
		public void loadObjects(Level newLevel) {
			potions = new ArrayList<>(newLevel.getPotions());
			containers = new ArrayList<>(newLevel.getContainers());
			spikes= newLevel.getSpikes();
			
		}
		
		private void loadImgs() {
			BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
			potionImgs = new BufferedImage[2][7];
			
			for(int j = 0; j< potionImgs.length; j++)
				for(int i = 0; i<potionImgs[j].length; i++)
					potionImgs[j][i] = potionSprite.getSubimage(12*i,  16*j,  12,  16);
			
			BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
			containerImgs = new BufferedImage[2][8];
			
			for(int j = 0; j< containerImgs.length; j++)
				for(int i = 0; i<containerImgs[j].length; i++)
					containerImgs[j][i] = containerSprite.getSubimage(40*i,  30*j,  40,  30);
			
			spikeImg= LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
		}
		public void update() {
			for(Potion p: potions)
				if(p.isActive())
					p.update();
			
			for(GameContainer gc : containers)
				if(gc.isActive())
					gc.update();
			
			
}
		public void draw(Graphics g, int xLvlOffSet) {
			drawPotions(g,xLvlOffSet);
			drawContainers(g,xLvlOffSet);
			drawTraps(g, xLvlOffSet);
			
			
						
					}
		private void drawTraps(Graphics g, int xLvlOffSet) {
				for(Spike s : spikes) {
					g.drawImage(spikeImg, (int)(s.getHitbox().x- xLvlOffSet), (int)(s.getHitbox().y-s.getyDrawOffset()), SPIKE_WIDTH,SPIKE_HEIGHT,null);
				
				// Spike hitbox
//		        g.setColor(Color.RED);
//		        g.drawRect(
//		            (int)(s.getHitbox().x - xLvlOffSet),
//		            (int)(s.getHitbox().y),
//		            (int)(s.getHitbox().width),
//		            (int)(s.getHitbox().height) 
//		            );
		            
				}
		
			}
			
					private void drawContainers(Graphics g, int xLvlOffSet) {
			for (GameContainer gc : containers) {
				if (gc.isActive()) {
					int type = 0;
					if (gc.getObjType() == BARREL)
						type = 1;

					g.drawImage(containerImgs[type][gc.getAniIndex()],
						(int)(gc.getHitbox().x - gc.getxDrawOffSet() - xLvlOffSet),
						(int)(gc.getHitbox().y - gc.getyDrawOffset()),
						CONTAINER_WIDTH,
						CONTAINER_HEIGHT,
						null);
				}
			}
		}
		private void drawPotions(Graphics g, int xLvlOffSet) {
			for(Potion p: potions)
				if(p.isActive()) {
					int type = 0;
					if(p.getObjType() == RED_POTION)
						type = 1;
					g.drawImage(potionImgs[type][p.getAniIndex()],
							(int)(p.getHitbox().x - p.getxDrawOffSet() - xLvlOffSet),
							(int)(p.getHitbox().y - p.getyDrawOffset()),
							POTION_WIDTH,
							POTION_HEIGHT,
							null);
					
				}
			
		}
		
		public void resetAllObjects() {

			System.out.println("Size of arrays: "+potions.size()+ " - "+containers.size());

			loadObjects(playing.getLevelManager().getCurrentLevel());
			for(Potion p: potions)
				p.reset();
			
			for (GameContainer gc : containers)
				gc.reset();
				System.out.println("Size of arrays after: "+potions.size()+ " - "+containers.size());
		}
		
}
