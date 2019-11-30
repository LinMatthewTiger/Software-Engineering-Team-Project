package ServerInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

import Database.Database;
import ServerComm.GameServer;

public class ServerGUI extends JFrame
{
  private JLabel status; //Initialized to �Not Connected�
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JLabel statusText;
	private JPanel statusPanel;
	private JPanel informationPanel;
	private JPanel labelTextFieldWrapper;
	private JLabel PortLabel;
	private JPanel PortPanel;
	private JLabel TimeoutLabel;
	private JPanel TimeoutPanel;
	private JButton listen;
	private JButton close;
	private JButton stop;
	private JPanel south;
	private JScrollPane logScroll;
	private JPanel logScrollWrapper;
	private JLabel logLabel;
	private JPanel textAreas;
	private JButton quit;
	
	private GameServer server;
	private Database db;
	
  public ServerGUI(String title)
	{
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		statusText = new JLabel("Status:");
		status = new JLabel("Not Connected");
		status.setForeground(Color.red);
		
		statusPanel = new JPanel(new FlowLayout());
		statusPanel.add(statusText);
		statusPanel.add(status);
		
		informationPanel = new JPanel();
		informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));
		informationPanel.add(statusPanel);
		informationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		labelTextFieldWrapper = new JPanel();
		labelTextFieldWrapper.setLayout(new BoxLayout(labelTextFieldWrapper, BoxLayout.PAGE_AXIS));
		informationPanel.add(labelTextFieldWrapper);
		
		//Port #
		PortLabel = new JLabel(labels[0], JLabel.RIGHT);
		textFields[0] = new JTextField(10);

		PortPanel = new JPanel();
		PortPanel.setLayout(new BoxLayout(PortPanel, BoxLayout.LINE_AXIS));
		PortPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		PortPanel.add(PortLabel);
		PortPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		PortPanel.add(textFields[0]);	
		PortPanel.setMaximumSize(new Dimension(250,25));	
		labelTextFieldWrapper.add(PortPanel);
		labelTextFieldWrapper.add(Box.createRigidArea(new Dimension(0, 15)));
		
		//Timeout
		TimeoutLabel = new JLabel(labels[1], JLabel.RIGHT);
		textFields[1] = new JTextField(5);
		
		TimeoutPanel = new JPanel();
		TimeoutPanel.setLayout(new BoxLayout(TimeoutPanel, BoxLayout.LINE_AXIS));
		TimeoutPanel.add(Box.createRigidArea(new Dimension(38, 0)));
		TimeoutPanel.add(TimeoutLabel);
		TimeoutPanel.add(Box.createRigidArea(new Dimension(10, 0)));
		TimeoutPanel.add(textFields[1]);	
		TimeoutPanel.add(Box.createRigidArea(new Dimension(90, 0)));
		TimeoutPanel.setMaximumSize(new Dimension(250,25));	
		labelTextFieldWrapper.add(TimeoutPanel);
		labelTextFieldWrapper.add(Box.createRigidArea(new Dimension(0, 15)));
		
		textAreas = new JPanel();
		textAreas.setLayout(new BoxLayout(textAreas, BoxLayout.PAGE_AXIS));
		
		//log
		logLabel = new JLabel("Server Log Below",JLabel.CENTER);
		logLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		textAreas.add(logLabel);
	
		log = new JTextArea(15,30);	
		logScroll = new JScrollPane(log);
		logScrollWrapper = new JPanel();
		logScrollWrapper.add(logScroll);
		textAreas.add(logScrollWrapper);
		
		//4 Jbuttons
		listen = new JButton("Listen");
		close = new JButton("Close");
		stop = new JButton("Stop");
		quit =  new JButton("Quit");

		south = new JPanel(new FlowLayout());
		south.add(listen);
		south.add(close);
		south.add(stop);
		south.add(quit);

		listen.addActionListener(new EventHandler());
		close.addActionListener(new EventHandler());
		stop.addActionListener(new EventHandler());
		quit.addActionListener(new EventHandler());
		
		
		this.add(informationPanel, BorderLayout.NORTH);
		this.add(textAreas, BorderLayout.CENTER);
		this.add(south, BorderLayout.SOUTH);
		
		try
		{
			server = new GameServer();
		} catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		db = new Database();
		try
		{
			db.setConnection("db.properties");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.setDatabase(db);

		server.setLog(log);
		server.setStatus(status);
		
		textFields[0].setText("8300");
		textFields[1].setText("500");
		
		setSize(300, 400);
		setVisible(true);
	}
	
	private class EventHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource().equals(listen))
			{
				String port = textFields[0].getText();
				String timeout = textFields[1].getText();
				if(textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty()) {
					log.append("Port Number/timeout not entered before pressing Listen\n");
				} else {
					server.setPort(Integer.parseInt(port));
					server.setTimeout(Integer.parseInt(timeout));
					try {
						server.listen();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (event.getSource().equals(stop))
			{
				if(!server.isListening()) {
					log.append("Server not currently started\n");
				} else {
					server.stopListening();
				}
			} else if (event.getSource().equals(close))
			{
				if(!server.isListening() && !status.getText().equals("Stopped")) {
					log.append("Server not currently started\n");
				} else {
					try {
						server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else if (event.getSource().equals(quit)) {
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args)
	{
		ServerGUI s = new ServerGUI("Server"); // args[0] represents the title of the GUI
		s.pack();
		s.setSize(new Dimension(400,500));
	}
}

