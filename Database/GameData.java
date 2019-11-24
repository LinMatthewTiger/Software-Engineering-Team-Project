package Database;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {
	final int WIDTH = 750, HEIGHT = 500;

	public int pacmanWidth = 30;
	public int pacmanHeight = 30;
	public int speed = 8;

	public int player1X = WIDTH / 2 - (2*pacmanWidth);
	public int player1Y = HEIGHT / 2 - (2*pacmanHeight);
	public int player2X = WIDTH / 2 + pacmanWidth;
	public int player2Y = HEIGHT / 2 - (2*pacmanHeight);
	
	public int player1Score = 0;
	public int player2Score = 0;

	//when client sends it back to the server this matters
	public boolean isPlayer1;
	
	public int timeLeft = 60;
	
	//Timer timer;
	
	public ArrayList<Point> pellets;
	public int pellet_size = 8;
	public Color pellet_color = new Color(235, 52, 110);
	
	public void generatePelletsCoordinates() 
	{
		ArrayList<Point> pellets = new ArrayList<>();
		int num_pellets = 20;
		
		for(int i = 0; i < 20; i++) 
		{
			int x = (int)(Math.random()*WIDTH);
			int y = (int)(Math.random()*HEIGHT);
			pellets.add(new Point(x,y));
		}
		this.pellets = pellets;
	}
	
	public boolean legalMove(int x, int y) 
	{
		boolean insideXAxis = (x + (pacmanWidth/2)> 0) && ((x + pacmanWidth) < WIDTH);
		boolean insideYAxis = (y + (pacmanHeight/2) > 0) && ((y + (2*pacmanHeight)) < HEIGHT);
		return insideXAxis && insideYAxis;
	}
	
	public void checkForPelletCollision(int x, int y, boolean isPlayer1) 
	{
		int x2 = x + pacmanWidth;
		int y2 = y + pacmanHeight;
		
		for(int i = 0; i < pellets.size(); i++) 
		{
			int pellet_x = pellets.get(i).x;
			int pellet_y = pellets.get(i).y;
			if(	(pellet_x > x && pellet_x < x  + pacmanWidth) && (pellet_y > y && pellet_y < y  + pacmanHeight)) 
			{
				int notImportant = isPlayer1 ? player1Score++ : player2Score++;
				pellets.remove(i);
			}
		}
	}
	
	public void gameOver()
	{
		
	}
}
