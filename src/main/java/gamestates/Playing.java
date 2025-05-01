package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.PauseOverlay;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import utilz.LoadSave;

import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods {
    private Player player1;
    private Player player2;
    private LevelManager levelManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    private boolean gameOver;
    private boolean lvlCompleted = false;
    private boolean playerDying;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (70 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));

        calcLvlOffset();
        loadStartLevel();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        objectManager = new ObjectManager(this);

        BufferedImage player1Sprite = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        BufferedImage player2Sprite = LoadSave.GetSpriteAtlas(LoadSave.PLAYER2_ATLAS);

        player1 = new Player(200, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), this, player1Sprite);
        player2 = new Player(260, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), this, player2Sprite);

        player1.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player2.loadLvlData(levelManager.getCurrentLevel().getLevelData());

        Point spawn = levelManager.getCurrentLevel().getPlayerSpawn();
        player1.setSpawn(spawn);
        player2.setSpawn(new Point(spawn.x + 60, spawn.y));

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    private void loadStartLevel() {
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        Point spawn = levelManager.getCurrentLevel().getPlayerSpawn();
        player1.setSpawn(spawn);
        player2.setSpawn(new Point(spawn.x + 60, spawn.y));
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        }else if(gameOver) {
        	gameOverOverlay.update();
        	
        
        } else if (playerDying) {
        	player1.update();
        	player2.update();
        	
        } else  {
            levelManager.update();
            objectManager.update();
            player1.update();
            player2.update();
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX1 = (int) player1.getHitbox().x;
        int playerX2 = (int) player2.getHitbox().x;
        int playerX = Math.max(playerX1, playerX2);
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder)
            xLvlOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLvlOffset += diff - leftBorder;

        if (xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        drawClouds(g);
        levelManager.draw(g, xLvlOffset);
        player1.render(g, xLvlOffset);
        player2.render(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (lvlCompleted) {
            levelCompletedOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++)
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying=false;
        player1.resetAll();
        player2.resetAll();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkPotionTouched(Rectangle2D.Float hitBox) {
        objectManager.checkObjectTouched(hitBox);
    }

    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    public void windowFocusLost() {
        player1.resetDirBooleans();
        player2.resetDirBooleans();
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver && paused)
            pauseOverlay.mouseDragged(e);
    }
    
    @Override
    public void mouseCLicked(MouseEvent e) {
        if (!gameOver && e.getButton() == MouseEvent.BUTTON1) {
            player1.setAttacking(true);
            player2.setAttacking(true);
        }
    }
    

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mousePressed(e);
        }else 
        	gameOverOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseReleased(e);
        }else 
        	gameOverOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (lvlCompleted)
                levelCompletedOverlay.mouseMoved(e);
        }else 
        	gameOverOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverOverlay.keyPressed(e);
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player1.setUp(true);
            case KeyEvent.VK_A -> player1.setLeft(true);
            case KeyEvent.VK_S -> player1.setDown(true);
            case KeyEvent.VK_D -> player1.setRight(true);
            case KeyEvent.VK_SPACE -> player1.setJump(true);

            case KeyEvent.VK_UP -> player2.setUp(true);
            case KeyEvent.VK_LEFT -> player2.setLeft(true);
            case KeyEvent.VK_DOWN -> player2.setDown(true);
            case KeyEvent.VK_RIGHT -> player2.setRight(true);
            case KeyEvent.VK_ENTER -> player2.setJump(true);

            case KeyEvent.VK_ESCAPE -> paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player1.setUp(false);
            case KeyEvent.VK_A -> player1.setLeft(false);
            case KeyEvent.VK_S -> player1.setDown(false);
            case KeyEvent.VK_D -> player1.setRight(false);
            case KeyEvent.VK_SPACE -> player1.setJump(false);

            case KeyEvent.VK_UP -> player2.setUp(false);
            case KeyEvent.VK_LEFT -> player2.setLeft(false);
            case KeyEvent.VK_DOWN -> player2.setDown(false);
            case KeyEvent.VK_RIGHT -> player2.setRight(false);
            case KeyEvent.VK_ENTER -> player2.setJump(false);
        }
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public Player getPlayer() {
        return player1;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void setPlayerDying(boolean playerDying) {
		this.playerDying = playerDying;

	}

	
}

