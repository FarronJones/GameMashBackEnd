package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods{

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg, backgroundImgPink;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackgrounds();
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);
    }

    private void loadBackgrounds(){
        backgroundImg= LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth= (int)(backgroundImg.getWidth()*Game.SCALE);
        menuHeight= (int)(backgroundImg.getHeight()*Game.SCALE);
        menuX=Game.GAME_WIDTH/2 - menuWidth/2;
        menuY= (int)(45 * Game.SCALE);
    }

    private void loadButtons(){
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2, (int)(150*Game.SCALE),0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2, (int)(220*Game.SCALE),1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2, (int)(290*Game.SCALE),2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for(MenuButton mb: buttons)
            mb.update();
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void draw(Graphics g) {
    	
    	g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
    	
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        for (MenuButton mb : buttons)
            mb.draw(g);
       // throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    //@Override
    public void mouseClicked(MouseEvent e) {
        
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'mouseCLicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons){
            if(isIn(e, mb)){
                mb.setMousePressed(true);
                break;
            }
        }

        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons){
            if(isIn(e, mb)){
                if(mb.isMousePressed())
                    mb.applyGamestate();
                	if(mb.getState()==Gamestate.PLAYING)
                	   game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLevelIndex());
                    break;
            }
        }
        resetButtons();
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    private void resetButtons(){
        for (MenuButton mb: buttons){
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb: buttons)
            mb.setMouseOver(false);

            for (MenuButton mb: buttons)
                if(isIn(e, mb)){
                    mb.setMouseOver(true);
                    break;
    }
        //throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
            Gamestate.state= Gamestate.PLAYING;
    
        //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
    }

    @Override
    public void keyReleased(KeyEvent e) {

        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void mouseCLicked(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseCLicked'");
    }

}
