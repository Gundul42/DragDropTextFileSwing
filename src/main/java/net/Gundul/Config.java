package net.Gundul;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import java.io.File;

public class Config
{
	private final String username;
	private final String password;
	private final String url;


	public Config(String username, String password, String url)
	{
		this.url = url;
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

	public  void fileUpload (File file) throws FileNotFoundException, ConnectException
	{
		String username = getUsername();
		String password = getPassword();
		String filePath = file.getPath();
		String nextcloudUrl = url + "/remote.php/dav/" + "files/" + username + "/";

		// Encode the credentials for Basic Authentication
		String auth = username + ":" + password;
		String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

		// Create the HttpClient
		HttpClient client = HttpClient.newHttpClient();

		try
		{
		// Create the HttpRequest
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(nextcloudUrl + Paths.get(filePath).getFileName().toString()))
				.header("Authorization", "Basic " + encodedAuth)
				.PUT(HttpRequest.BodyPublishers.ofFile(Path.of(filePath)))
				.build();
			// Send the request
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// Print the response status and body
			System.out.println("Response Code: " + response.statusCode());
			System.out.println("Response Body: " + response.body());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}