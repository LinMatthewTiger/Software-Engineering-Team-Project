package ClientInterface;

import static org.junit.Assert.*;

import java.awt.Dimension;

import javax.swing.JButton;

import org.junit.Test;

import ServerInterface.ServerGUI;

public class CreateAccountPanelTest
{
	private JButton listen;
	
	public static void main(String[] args)
  {
		ServerGUI s = new ServerGUI("Server");
		s.pack();
		s.setSize(new Dimension(400,500));
		
		
  }
}
