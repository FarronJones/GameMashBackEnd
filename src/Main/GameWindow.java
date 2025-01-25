package Main;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	  private JFrame jframe;
	  public GameWindow(GamePanel gamePanel) {
		  jframe = new JFrame();
		  jframe.setSize(400, 400);
		  jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  jframe.add(gamePanel);
		  jframe.setVisible(true);
	  }
}
