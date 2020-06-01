package zohobooks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TokenConfig {
	private String access_token="";
	String refresh_token="1000.9c5071b7704ae16a276ab12c2f5a62ed.57eedc3f27a0ecfb83e3c517d4db9468&client_id=1000.F20ZMG4L3Q9C98HOJ6YQMRZRGJMPEH&client_secret=ecc5867a4567f9db106278678e551cbeb23f503fd5";
	String organisation_id = "715673375";
	public void setAccess(String access_token)
	{
		this.access_token = access_token;
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
}
