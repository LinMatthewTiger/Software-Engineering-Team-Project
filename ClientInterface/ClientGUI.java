package ClientInterface;

import javax.swing.*;

import ClientComm.CreateAccountControl;
import ClientComm.InitialControl;
import ClientComm.LoginControl;
import ServerComm.GameClient;

import java.awt.*;
import java.io.IOException;

public class ClientGUI extends JFrame
{
	// Constructor that creates the client GUI.
	public ClientGUI()
	{

		// Set the title and default close operation.
		this.setTitle("Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GameClient client = new GameClient();
		try
		{
			client.openConnection();
			//make a game panel

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		//Create the Controllers next
		//Next, create the Controllers
		InitialControl ic = new InitialControl(container); 
		LoginControl lc = new LoginControl(container, client); //Probably will want to pass in ChatClient here
		CreateAccountControl cc = new CreateAccountControl(container, client);

		// Create the four views. (need the controller to register with the Panels
		JPanel view1 = new InitialPanel(ic);
		JPanel view2 = new LoginPanel(lc);
		JPanel view3 = new CreateAccountPanel(cc);

		client.container = container;
		client.cardLayout = cardLayout;

		// Add the views to the card layout container.
		container.add(view1, "1");
		container.add(view2, "2");
		container.add(view3, "3");

		// Show the initial view in the card layout.
		cardLayout.show(container, "1");

		// Add the card layout container to the JFrame.
		this.add(container, BorderLayout.CENTER);

		// Show the JFrame.
		this.getContentPane().setPreferredSize(new Dimension(750,500));
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args)
	{
		new ClientGUI();
	}
}
