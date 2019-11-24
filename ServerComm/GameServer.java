package ServerComm;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import javax.swing.*;

import Database.CreateAccountData;
import Database.Database;
import Database.LoginData;
import ServerInterface.ServerGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameServer extends AbstractServer 
{
	private JTextArea log;
	private JLabel status;
	private Database database;

	//Store instances of the server GUI so we can manipulate
	private ServerGUI gui;
	private GameLogicControlServer glcs;

	public GameServer() 
	{
		super(8300);
		glcs = new GameLogicControlServer();
		glcs.server = this;
		database = new Database();
		try
		{
			database.setConnection("db.properties");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public GameServer(int port) 
	{
		super(port);
	}

	//Set the server GUI
	public void setGui(ServerGUI gui) 
	{
		this.gui = gui;
	}

	public void setDatabase(Database database) 
	{
		this.database = database;
	}

	public void setLog(JTextArea log) 
	{
		this.log = log;
		glcs.log = this.log;
		log.append(log.getText());
	}

	public JTextArea getLog() 
	{
		return log;
	}

	public void setStatus(JLabel status) 
	{
		this.status = status;
		status.setText(status.getText());
	}

	public JLabel getStatus() 
	{
		return status;
	}

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) 
	{		
		if (arg0 instanceof String) 
		{
			String msg = (String)arg0;
			if (msg.startsWith("request results"))
			{
				String[] data = msg.split(",");
				int p1score = Integer.parseInt(data[1]);
				int p2score = Integer.parseInt(data[2]);
				
				database.updateScore(p1score, glcs.player1name);
				database.updateScore(p2score, glcs.player2name);
				
				ArrayList<String> standings = database.standings();
				try
				{
					arg1.sendToClient(standings);
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				glcs.handleDataFromClient(msg);
			}
		}
		
		else if (arg0 instanceof LoginData)
		{
			log.append("Log In info from Client " + arg1.getId() + "\n");

			LoginData loginData = (LoginData)arg0;

			//If can't find user, send error msg to client
			if (database.findUser(loginData) == null)
			{
				log.append("Cannot find Client " + arg1.getId() + "\n");
				try
				{
					arg1.sendToClient("login unsuccessful");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else 
			{
				log.append("Log in for Client " + arg1.getId() + " successful\n");
				glcs.handleClientConnection(arg1);
				glcs.handleDataFromClient("username:" + loginData.getUsername());
				try
				{
					arg1.sendToClient("login successful");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		else if (arg0 instanceof CreateAccountData)
		{
			log.append("Create Account info from Client " + arg1.getId() + "\n");
			CreateAccountData createData = (CreateAccountData)arg0;
			
			if (database.addUser(createData))
			{
				log.append("Create Account for Client " + arg1.getId() + " successful\n");
				try
				{
					arg1.sendToClient("created");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				log.append("Username already existed\n");
				try
				{
					arg1.sendToClient("not unique");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	protected void listeningException(Throwable exception) 
	{
		log.append(exception.getMessage() + "\n");
		status.setText("Exception Occurred when Listening");
		status.setForeground(Color.RED);
		log.append("Click Listen again to restart the server.\nMake sure all invalid requests are stopped\n");
	}

	protected void serverStarted() 
	{
		log.append("Server Started\n");
		status.setText("Listening");
		status.setForeground(Color.GREEN);
	}

	protected void serverStopped() 
	{
		try 
		{
			Thread.sleep(10);// this will allow serverStopped to process first
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.append("Server and all current clients are closed - Press Listen to Restart\n");
		status.setText("Close");
		status.setForeground(Color.RED);
	}

	protected void serverClosed() 
	{
		log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
		status.setText("Stopped");
		status.setForeground(Color.RED);
		glcs.resetState();
	}

	// when a client connects
	protected void clientConnected(ConnectionToClient client) 
	{
		//glcs.handleClientConnection(client);
	}
}
