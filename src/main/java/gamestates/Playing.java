package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused=false;

    public Playing(Game game) {
            super(game);
            initClasses();
        }
    




        private void initClasses() {
			levelManager = new LevelManager(game);
			player = new Player(200,200,(int)(32*Game.SCALE),(int)(32*Game.SCALE));
			player.loadLvlData(levelManager.getCurrentLevel().getlvlData());
            pauseOverlay=new PauseOverlay(this);
		}

        public void windowFocusLost() {
			player.resetDirBooleans();
		}
		
		public Player getPlayer() {
			return player;
		}





        @Override
        public void update() {
            if(!paused){
                levelManager.update();
                player.update();
            }else{
                pauseOverlay.update();
            }
          //  throw new UnsupportedOperationException("Unimplemented method 'update'");
        }





        @Override
        public void draw(Graphics g) {
           levelManager.draw(g);
           player.render(g);
           if(paused)
            pauseOverlay.draw(g);
           // throw new UnsupportedOperationException("Unimplemented method 'draw'");
        }


        public void mouseDragged(MouseEvent e){
            if(paused)
                pauseOverlay.mouseDragged(e);
        }


        @Override
        public void mouseCLicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
		
           // throw new UnsupportedOperationException("Unimplemented method 'mouseCLicked'");
        }






        @Override
        public void mousePressed(MouseEvent e) {
          if(paused)
          pauseOverlay.mousePressed(e);
        }





        @Override
        public void mouseReleased(MouseEvent e) {
            if(paused)
            pauseOverlay.mouseReleased(e);
        }





        @Override
        public void mouseMoved(MouseEvent e) {
            if(paused)
            pauseOverlay.mouseMoved(e);
        }


        public void unpauseGame(){
            paused=false;

        }


        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_W:
                  player.setUp(true);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_S:
                   player.setDown(true);
                    break;
                case KeyEvent.VK_D:
                  player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                    case KeyEvent.VK_ESCAPE:
                    paused=!paused;
                    break;
            }	
            //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
        }





        @Override
        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_W:
                    player.setUp(false);
                    break;
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_S:
                    player.setDown(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
            
            //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
        }
}
