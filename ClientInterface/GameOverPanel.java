package ClientInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import ClientComm.GameOverControl;
import Database.GameData;
import ServerComm.GameClient;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GameOverPanel extends JPanel 
{
  final int WIDTH = 752, HEIGHT = 501;
	boolean isPlayer1;
	GameData data;
	public GameClient client;
	GamePanelClient currentGame;
	
	private JLabel player1ScoreValue;
  private JLabel player2ScoreValue;
  private DefaultTableModel model;
  
	public GameOverPanel(GameData data, GameOverControl goc) {
		
		this.setSize(new Dimension(752, 501));
		// keyboard listener related
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setLayout(null);
		
		JButton btnPlayAgain = new JButton("Play Again");
		btnPlayAgain.setBounds(228, 428, 117, 29);
		add(btnPlayAgain);
		
		JButton btnExitGame = new JButton("Exit Game");
		btnExitGame.setBounds(431, 428, 117, 29);
		add(btnExitGame);
		
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameOver.setBounds(75, 57, 597, 50);
		add(lblGameOver);
		
		JLabel lblWinner = new JLabel("WINNER");
		lblWinner.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblWinner.setHorizontalAlignment(SwingConstants.CENTER);
		lblWinner.setBounds(75, 105, 597, 29);
		add(lblWinner);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setBounds(75, 134, 597, 29);
		add(lblPlayer);
		
	  JLabel player1ScoreText = new JLabel("Player 1 Score");
    player1ScoreText.setHorizontalAlignment(SwingConstants.CENTER);
    player1ScoreText.setBounds(75, 11, 102, 34);
    add(player1ScoreText);
    
    JLabel player2ScoreText = new JLabel("Player 2 Score");
    player2ScoreText.setHorizontalAlignment(SwingConstants.CENTER);
    player2ScoreText.setBounds(570, 11, 102, 34);
    add(player2ScoreText);
    
    player1ScoreValue = new JLabel("0");
    player1ScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
    player1ScoreValue.setBounds(84, 41, 81, 16);
    add(player1ScoreValue);
    
    player2ScoreValue = new JLabel("0");
    player2ScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
    player2ScoreValue.setBounds(580, 41, 81, 16);
    add(player2ScoreValue);
    
    JLabel lblHighScore = new JLabel("High Scores");
    lblHighScore.setHorizontalAlignment(SwingConstants.CENTER);
    lblHighScore.setBounds(75, 204, 597, 16);
    add(lblHighScore);
    
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(228, 232, 312, 184);
    add(scrollPane);
    
    model = new DefaultTableModel(); 
    JTable scoreArea = new JTable(model);
    model.addColumn("Player");
    model.addColumn("High Score");
    scrollPane.setViewportView(scoreArea);
    
    //update the scores
//    player1ScoreValue.setText(String.valueOf(data.getPlayer1Score()));
//    player2ScoreValue.setText(String.valueOf(data.getPlayer2Score()));
    
//    int p1Score = Integer.parseInt(data.getPlayer1Score());
//
//    int p2Score = Integer.parseInt(data.getPlayer1Score());
//    
//    if(p1Score > p2Score) {
//      lblPlayer.setText(String.valueOf(data.getPlayer1Score()));
//    } else
//    {
//      lblPlayer.setText(String.valueOf(data.getPlayer2Score()));
//    }
	}
	
	public DefaultTableModel getScoreAreaModel()
	{
		return model;
	}
}

