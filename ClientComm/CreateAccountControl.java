package ClientComm;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import ClientInterface.CreateAccountPanel;
import Database.CreateAccountData;
import ServerComm.GameClient;

public class CreateAccountControl implements ActionListener
{
	  // Private data fields for the container and chat client.
	  private JPanel container;
	  private GameClient client;
	  
	  
	  // Constructor for the login controller.
	  public CreateAccountControl(JPanel container, GameClient client)
	  {
	    this.container = container;
	    this.client = client;
	    client.setCreateAccountControl(this);
	  }
	  
	  // Handle button clicks.
	  public void actionPerformed(ActionEvent ae)
	  {
	    // Get the name of the button clicked.
	    String command = ae.getActionCommand();

	    // The Cancel button takes the user back to the initial panel.
	    if (command == "Cancel")
	    {
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "1");
	    }

	    // The Submit button submits the login information to the server.
	    else if (command == "Submit")
	    {
			CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
	    	
	    	//check password verification
	    	boolean passwordMatched = passwordVerified(createAccountPanel.getPassword(), createAccountPanel.getPasswordVerification());
	 
	    	
	    	// Get the username and password the user entered.
			CreateAccountData data = new CreateAccountData(createAccountPanel.getUsername(), createAccountPanel.getPassword());
	      
	      // Check the validity of the information locally first.
			if(!enoughLength(createAccountPanel.getPassword())) {
		        displayError("password must be at least 6 characters.");
		        return;
			}
			else if (!passwordMatched)
			  {
			    displayError("passwords entered do not match.");
			    return;
			  }

	      // Submit the create account information to the server.
		      try {
				client.sendToServer(data);
				} 
		      catch (IOException e){
					e.printStackTrace();
				}
	    }
	  }
	  
	  public void notUniqueUsername() {
		  CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		  createAccountPanel.setError("username not unique");
	  }
	  
	  public void createdUser() {
		  CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		  createAccountPanel.setError("");
		  createAccountPanel.clearFields();
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "2");
	      
	  }
	  
	  public boolean passwordVerified(String s1, String s2) {
		  return s1.equals(s2);
	  }
	  
	  public boolean enoughLength(String s1) {
		  return s1.length() >= 6;
	  }
	  

	  // Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	  public void displayError(String error)
	  {
		  CreateAccountPanel createAccountPanel = (CreateAccountPanel)container.getComponent(2);
		  createAccountPanel.setError(error);
	    
	  }
	}