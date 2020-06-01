package zohobooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenConfig {
	private String inputLine="";
	private String access_token="";
	private String refresh_token="1000.9c5071b7704ae16a276ab12c2f5a62ed.57eedc3f27a0ecfb83e3c517d4db9468";
	private String client_id="1000.F20ZMG4L3Q9C98HOJ6YQMRZRGJMPEH";
	private String client_secret="ecc5867a4567f9db106278678e551cbeb23f503fd5";
	public void setAccess(String access_token)
	{
		this.access_token = access_token;
		setInputLine("Zoho-oauthtoken "+getAccess());
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
