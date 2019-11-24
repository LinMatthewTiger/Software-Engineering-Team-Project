package ClientInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Database.GameData;
import ServerComm.GameClient;

import java.awt.Font;

public class GamePanelClient extends JPanel 
{
	int pacmanWidth;
	int pacmanHeight;
	int player1X;
	int player1Y;
	int player2X;
	int player2Y;
	int speed;
	boolean isPlayer1;
	GameData data;
	private KeyBoardListener ky;
	public GameClient client;
	private JLabel SecondsLeftValue;

	//pellets stuff
	ArrayList<Point> pellets = new 	ArrayList<Point>();
	int pellet_size = 8;
	Color pellet_color = new Color(235, 52, 110);
	private JLabel player1ScoreValue;
	private JLabel player2ScoreValue;

	public GamePanelClient(GameData data) {
		this.data = data;
		pacmanWidth = data.pacmanWidth;
		pacmanHeight = data.pacmanHeight;
		isPlayer1 = data.isPlayer1;
		speed = data.speed;
		player1X = data.player1X;
		player1Y = data.player1Y;
		player2X = data.player2X;
		player2Y = data.player2Y;

		//pellets stuff
		pellets = data.pellets;
		pellet_size = data.pellet_size;
		pellet_color = data.pellet_color;

		this.setSize(new Dimension(752, 501));
		// keyboard listener related
		ky = new KeyBoardListener();
		addKeyListener(ky);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(null);



		JLabel SecondsLeftText = new JLabel("Seconds Left");
		SecondsLeftText.setHorizontalAlignment(SwingConstants.CENTER);
		SecondsLeftText.setBounds(285, 9, 136, 16);
		add(SecondsLeftText);

		SecondsLeftValue = new JLabel("60");
		SecondsLeftValue.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SecondsLeftValue.setHorizontalAlignment(SwingConstants.CENTER);
		SecondsLeftValue.setBounds(332, 29, 50, 34);
		add(SecondsLeftValue);

		JLabel player1ScoreText = new JLabel("Player 1 Score");
		player1ScoreText.setHorizontalAlignment(SwingConstants.CENTER);
		player1ScoreText.setBounds(72, 0, 102, 34);
		add(player1ScoreText);

		JLabel player2ScoreText = new JLabel("Player 2 Score");
		player2ScoreText.setHorizontalAlignment(SwingConstants.CENTER);
		player2ScoreText.setBounds(545, 0, 102, 34);
		add(player2ScoreText);

		player1ScoreValue = new JLabel("0");
		player1ScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
		player1ScoreValue.setBounds(82, 27, 81, 16);
		add(player1ScoreValue);

		player2ScoreValue = new JLabel("0");
		player2ScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
		player2ScoreValue.setBounds(555, 27, 81, 16);
		add(player2ScoreValue);
		System.out.print("Height is " + this.getHeight());
	}

	// after we receive gameData from server we should could update which repaints
	// at the end
	public void updateGame(GameData data) {
		//update the players
		player1X = data.player1X;
		player1Y = data.player1Y;
		player2X = data.player2X;
		player2Y = data.player2Y;

		//update time
		SecondsLeftValue.setText(String.valueOf(data.timeLeft));

		//update the pellets
		pellets = data.pellets;

		//update the scores
		player1ScoreValue.setText(String.valueOf(data.player1Score));
		player2ScoreValue.setText(String.valueOf(data.player2Score));
		repaint();
	}
	
	public ArrayList<String> getScores()
	{
		ArrayList<String> scores = new ArrayList<String>();
		scores.add(player1ScoreValue.getText());
		scores.add(player2ScoreValue.getText());
		return scores;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//draw the players
		g.setColor(Color.YELLOW);
		g.fillOval(player1X, player1Y, pacmanWidth, pacmanHeight);
		g.setColor(Color.ORANGE);
		g.fillOval(player2X, player2Y, pacmanWidth, pacmanHeight);

		//draw the pellets
		if(pellets != null) {
			for(Point pellet : pellets) {
				g.setColor(pellet_color);
				g.fillOval(pellet.x, pellet.y, pellet_size, pellet_size);
			}
		}
	}

	public class KeyBoardListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			int xDifference = 0;
			int yDifference = 0;
			if (key == KeyEvent.VK_LEFT) {
				xDifference = -speed;
			}
			else if (key == KeyEvent.VK_RIGHT) {
				xDifference = speed;
			}
			else if (key == KeyEvent.VK_UP) {
				yDifference = -speed;
			}
			else if (key == KeyEvent.VK_DOWN) {
				yDifference = speed;
			}
			if(isPlayer1) {
				int player1XChange = player1X + xDifference;
				int player1YChange = player1Y + yDifference;
				try {
					client.sendToServer(String.format("GameDataFromClient,%d,%d,%b", player1XChange, player1YChange, isPlayer1));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				int player2XChange = player2X + xDifference;
				int player2YChange = player2Y + yDifference;
				try {
					client.sendToServer(String.format("GameDataFromClient,%d,%d,%b",player2XChange, player2YChange, isPlayer1));
				} catch (IOException e1) {
					e1.printStackTrace();
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
