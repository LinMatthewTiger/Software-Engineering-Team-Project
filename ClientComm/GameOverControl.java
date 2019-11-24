package ClientComm;

import java.awt.*;
import javax.swing.*;

import ServerComm.GameClient;

import java.awt.event.*;
import java.io.IOException;

public class GameOverControl implements ActionListener
{
  // Private data fields for the container and chat client.
  private JPanel container;
  private GameClient client;
  
  
  // Constructor for the login controller.
  public GameOverControl(JPanel container, GameClient client)
  {
    this.container = container;
    this.client = client;
    client.setGameOverControl(this);
  }
  
  // Handle button clicks.
  public void actionPerformed(ActionEvent ae)
  {
    // Get the name of the button clicked.
    String command = ae.getActionCommand();

    // The Cancel button takes the user back to the initial panel.
    if (command == "Play Again")
    {
      CardLayout cardLayout = (CardLayout)container.getLayout();
      cardLayout.show(container, "1");
    }

    // The Submit button submits the create account information to the server.
    else if (command == "Exit")
    {
      System.exit(0);
    }
//      // Get the username and password the user entered.
//      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
//      LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());
//      
//      // Check the validity of the information locally first.
//      if (data.getUsername().equals("") || data.getPassword().equals(""))
//      {
//        displayError("You must enter a username and password.");
//        return;
//      }
//
//      // Submit the login information to the server.
//      try
//    {
//        client.sendToServer(data);
//    } catch (IOException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//    }
  }

  // After the login is successful, set the User object and display the contacts screen. - this method would be invoked by 
  //the ChatClient
  public void loginSuccess()
  {
//      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
//    loginPanel.setError("");
//     CardLayout cardLayout = (CardLayout)container.getLayout();
//     cardLayout.show(container, "4");
  }
  
  public void loginNotValid() {
//      LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
//    loginPanel.setError("username and password are incorrect");
  }

  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
  public void displayError(String error)
  {
//    LoginPanel loginPanel = (LoginPanel)container.getComponent(1);
//    loginPanel.setError(error);
    
  }
}
