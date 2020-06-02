package zohobooks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenConfig {
	static Properties prop=new Properties();
	private String inputLine="";
	private String access_token="";
	private String refresh_token="";
	private String client_id="";
	private String client_secret="";
	public TokenConfig()
	{
		try {
				FileInputStream ip= new FileInputStream("config.properties");
				prop.load(ip);
				this.access_token = prop.getProperty("access_token");
				this.refresh_token = prop.getProperty("refresh_token");
				this.client_id = prop.getProperty("client_id");
				this.client_secret = prop.getProperty("client_secret");
				setInputLine("Zoho-oauthtoken "+getAccess());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	}
	public void setAccess(String a_t)
	{
		this.access_token = a_t;
		setInputLine("Zoho-oauthtoken "+getAccess());
		try {
			prop.setProperty("access_token", a_t);
			File configFile = new File("config.properties");
			FileWriter writer = new FileWriter(configFile);
			prop.store(writer, "host settings");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public String getAccess()
	{
		return access_token;
	}
	public String toPrettyFormat(String jsonString) 
	  {
	      JsonParser parser = new JsonParser();
	      JsonObject json = parser.parse(jsonString).getAsJsonObject();

	      Gson gson = new GsonBuilder().setPrettyPrinting().create();
	      String prettyJson = gson.toJson(json);

	      return prettyJson;
	  }
	public String getInputLine() {
		return inputLine;
	}
	public void setInputLine(String inputLine) {
		this.inputLine = inputLine;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
}
