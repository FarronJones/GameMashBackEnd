package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow extends JFrame {
	  private JFrame jframe;
	  public GameWindow(GamePanel gamePanel) {
		  jframe = new JFrame();
		
		  jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  jframe.add(gamePanel);
		  jframe.setLocationRelativeTo(null);
		  jframe.setResizable(false);
		  jframe.pack();
		  jframe.setVisible(true);
		  jframe.addWindowFocusListener(new WindowFocusListener() {
				  
			  	//To stop player from moving when window is out of focus
				@Override
				public void windowLostFocus(WindowEvent e) {
					gamePanel.getGame().windowFocusLost();
				}

				@Override
				public void windowGainedFocus(WindowEvent e) {
					// TODO Auto-generated method stub


				}
				  
	  	});
	  }
}
