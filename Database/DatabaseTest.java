package Database;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Database.Database;

public class DatabaseTest
{
	private String[] users = {"jsmith@uca.edu","msmith@uca.edu","tjones@yahoo.com","jjones@yahoo.com"};
	private String[] passwords = {"hello123","pass123","123456","hello1234"};
	private Database db;
	private String key = "key";

	@Before
	public void setUp() throws Exception 
	{
		db = new Database();
		db.setConnection("db.properties");
	}

	@Test
	public void testSetConnection() throws FileNotFoundException, IOException, SQLException
	{
		db.setConnection("db.properties");
		Connection con = db.getConnection();
		assertNotNull("setConnection returns null", con);
	}

	@Test
	public void testFindUser()
	{
		int rand_num = ((int)Math.random()*users.length);

		LoginData data = new LoginData(users[rand_num], passwords[rand_num]);
		ArrayList<String> result = db.findUser(data);

		assertNotNull("Can't find user", result);
	}
	
	@Test
	public void testAddUser()
	{
		String username = "111"; 
		String password = "111111";

		CreateAccountData data = new CreateAccountData(username, password);
		boolean result = db.addUser(data);

		assertEquals("Fail to create new account", result, true);
	}
	
	@Test
	public void testUpdateScore() throws SQLException
	{
		int rand_num = ((int)Math.random()*users.length);
		db.updateScore(100, users[rand_num]);
	}
}
