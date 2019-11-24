package ServerComm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanelServer extends JPanel {
	   private final int WIDTH = 750, HEIGHT = 500;
	   
	   int circleX = WIDTH/2;
	   int circleY = HEIGHT/2;
	   int pacmanWidth = 30;
	   int pacmanHeight = 30;
	   int speed = 8;
	   
	   private KeyBoardListener ky;
	   
	   public GamePanelServer() {
		   this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		   ky = new KeyBoardListener();
		   addKeyListener(ky);
		   setFocusable(true);
		   setFocusTraversalKeysEnabled(false);
	   }
	   
	   public void paintComponent (Graphics g)
	   {
		   super.paintComponent(g);
		   g.setColor(Color.YELLOW);
		   g.fillOval(circleX, circleY, pacmanWidth, pacmanHeight);
	   }
	   
	   public boolean legalMove(int x, int y) {
		   boolean insideXAxis = (x > 0) && ((x + pacmanWidth) < WIDTH);
		   boolean insideYAxis = (y > 0) && ((y + pacmanHeight) < HEIGHT);
		   return insideXAxis && insideYAxis;
	   }
	   
	   public class KeyBoardListener implements KeyListener {
		   
		   public KeyBoardListener() {
		   }
		   
		   public void keyPressed(KeyEvent e) {
			    int key = e.getKeyCode();
			    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			    	circleX -= speed;
			    	if (legalMove(circleX, circleY)) {
				    	repaint();
			    	} else {
			    		circleX += speed;
			    	}
			    }
	
			    else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			    	circleX += speed;
			    	if (legalMove(circleX, circleY)) {
				    	repaint();
			    	} else {
			    		circleX -= speed;
			    	}
			    }
	
			    else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
			    	circleY -= speed;
			    	if (legalMove(circleX, circleY)) {
				    	repaint();
			    	} else {
			    		circleY += speed;
			    	}
			    }
	
			    else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
			    	circleY += speed;
			    	if (legalMove(circleX, circleY)) {
				    	repaint();
			    	} else {
			    		circleY -= speed;
			    	}
			    }
			}
	
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
		
			@Override
			public void keyTyped(KeyEvent arg0) {

		}
		
	}
	   
}
