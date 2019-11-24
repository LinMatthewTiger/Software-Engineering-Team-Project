package guiMoving;

import java.awt.Color;
import java.io.Serializable;

public class Ghost implements Serializable  {
	public int X;
	public int Y;
	Color ghost_color = null;
	public int speed;
	public int Xchange;
	public int Ychange;
	public int size;
	public int HEIGHT;
	public int WIDTH;
	
	public Ghost(int x, int y, Color color, int speed, int size, int screen_width, int screen_height) {
		X = x;
		Y = y;
		this.ghost_color = color;
		this.speed = speed;
		this.size = size;
		WIDTH = screen_width;
		HEIGHT = screen_height;
	}
	
	public void moveGhost(int x, int y) {
		X += x;
		Y -= y;
	}
}
