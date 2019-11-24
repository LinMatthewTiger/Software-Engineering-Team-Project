package Database;

import java.io.Serializable;

public class CreateAccountData implements Serializable 
{
	  // Private data fields for the username and password.
	  private String username;
	  private String password;
	  private String passwordverification;
	  
	  // Getters for the username and password.
	  public String getUsername()
	  {
	    return username;
	  }
	  public String getPassword()
	  {
	    return password;
	  }
	  
	  public String getPasswordVerification() {
		  return passwordverification;
	  }
	  
	  // Setters for the username and password.
	  public void setUsername(String username)
	  {
	    this.username = username;
	  }
	  public void setPassword(String password)
	  {
	    this.password = password;
	  }
	  
	  public void setPasswordVerification(String passwordVerification) {
		  this.passwordverification = passwordVerification;
	  }
	  
	  // Constructor that initializes the username and password.
	  public CreateAccountData(String username, String password)
	  {
	    setUsername(username);
	    setPassword(password);
	  }
	}