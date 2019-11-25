package ServerComm;

import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import ClientComm.CreateAccountControl;
import ClientComm.LoginControl;
import ClientInterface.GamePanelClient;
import Database.GameData;
import ClientComm.GameOverControl;
import ClientInterface.GameOverPanel;
import ocsf.client.AbstractClient;

public class GameClient extends AbstractClient
{
	private LoginControl loginControl = null;
	private CreateAccountControl createAccountControl = null;
	private GameOverControl gameOverControl = null;
	GamePanelClient gp;
	GameOverPanel gameOver;
	public JPanel container;
	public CardLayout cardLayout;
	boolean start = true;
	
	private String p1Name;
	private String p2Name;
	private int numPlayers = 0;

	public GameClient()
	{
		super("localhost",8300);
	}

	@Override
	public void handleMessageFromServer(Object arg0)
	{
		if (arg0 instanceof String) 
		{
			String msg = (String)(arg0);
			
			if(msg.equals("login successful")) 
			{
				loginControl.loginSuccess();
			}
			else if (msg.equals("login unsuccessful")) 
			{
				loginControl.loginNotValid();
			}
			else if (msg.equals("not unique")) 
			{
				createAccountControl.notUniqueUsername();
			}
			else if (msg.equals("created")) 
			{
				createAccountControl.createdUser();
			}
			else
			{
				numPlayers++;
				if (numPlayers == 1)
				{
					p1Name = msg;
				}
				else if (numPlayers == 2)
				{
					p2Name = msg;
					numPlayers = 0;
				}
			}
		}
		else if (arg0 instanceof GameData) 
		{
			GameData data = (GameData)arg0;
			
			if (start) 
			{
				//initialize the game
				JPanel view5 = new GamePanelClient(data);
				gp = (GamePanelClient) view5; //client storage of gameclient
				container.add(view5, "5");
				cardLayout.show(container, "5");
				gp.client = this;
				start = false;
				view5.requestFocus();
			} 
			else
			{
				//update our game
				gp.updateGame(data);
				
				if(data.timeLeft <= 0) 
				{
					//initialize the game
					JPanel view6 = new GameOverPanel(data, gameOverControl);
					gameOver = (GameOverPanel) view6; //client storage of gameclient
					container.add(view6, "6");
					cardLayout.show(container, "6");
					gameOver.client = this;
					start = false;
					try
					{
						this.sendToServer("request results," + gp.getScores().get(0) + "," + gp.getScores().get(1));
					} catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else if (arg0 instanceof ArrayList)
		{
			ArrayList<String> standings = (ArrayList<String>) arg0;
			gameOverControl.displayStandings(standings, p1Name, p2Name);
			System.out.println(" ");
		}
	}

	public LoginControl getLoginControl() 
	{
		return loginControl;
	}

	public void setLoginControl(LoginControl loginControl) 
	{
		this.loginControl = loginControl;
	}

	public CreateAccountControl getCreateAccountControl() 
	{
		return createAccountControl;
	}

	public void setCreateAccountControl(CreateAccountControl createAccountControl) 
	{
		this.createAccountControl = createAccountControl;
	}

	public GameOverControl getGameOverControl() 
	{
		return gameOverControl;
	}

	public void setGameOverControl(GameOverControl gameOverControl) 
	{
		this.gameOverControl = gameOverControl;
	}
}
