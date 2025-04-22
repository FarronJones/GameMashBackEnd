package objects;
import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Spike extends GameObject{
    public Spike(int x, int y, int objType){
        super(x,y,objType);


        initHitbox(32, 4);
        xDrawOffset=0;
        yDrawOffset=(int)(Game.SCALE*16);
        hitbox.y+= yDrawOffset;

    }
    


}
