package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Game;

public class Menu extends State implements Statemethods{

    public Menu(Game game) {
        super(game);

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void draw(Graphics g) {
       g.setColor(Color.black);
       g.drawString("MENU", Game.GAME_WIDTH/2, 200);
       // throw new UnsupportedOperationException("Unimplemented method 'draw'");
    }

    //@Override
    public void mouseClicked(MouseEvent e) {
        
        // TODO Auto-generated method stub
       // throw new UnsupportedOperationException("Unimplemented method 'mouseCLicked'");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
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
