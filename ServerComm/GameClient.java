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
				JPanel view4 = new GamePanelClient(data);
				gp = (GamePanelClient) view4; //client storage of gameclient
				container.add(view4, "4");
				cardLayout.show(container, "4");
				gp.client = this;
				start = false;
				view4.requestFocus();
			} 
			else
			{
				//update our game
				gp.updateGame(data);
				
				if(data.timeLeft <= 0) 
				{
					gameOverControl = new GameOverControl(container, this);
					GameOverPanel view5 = new GameOverPanel(data, gameOverControl);
					gameOverControl.setScoreArea(view5.getScoreArea());
					//gameOver = view5; //client storage of gameclient
					container.add(view5, "5");
					cardLayout.show(container, "5");
					view5.client = this;
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
//			gameOverControl.displayStandings(standings, p1Name, p2Name);
			gameOverControl.displayStandings(standings);
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
