package net.Gundul;

public class Config
{
	private	final String		username;
	private final String		password;

	public Config(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public String getUsername()
	{
		return username;
	}
}
