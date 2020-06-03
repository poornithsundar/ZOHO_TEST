package zohobooks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ApiMethods 
{
	
	public static void gen_access_token(TokenConfig g)  throws IOException, InterruptedException, ParseException
	{
		String inputLine;
		StringBuffer content = new StringBuffer();
		URL url = new URL("https://accounts.zoho.com/oauth/v2/token?refresh_token="+g.getRefresh_token()+"&client_id="+g.getClient_id()+"&client_secret="+g.getClient_secret()+"&redirect_uri=http://www.zoho.com/books&grant_type=refresh_token");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		int status = con.getResponseCode();
		if(status==200)
		{
			BufferedReader in = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
					while ((inputLine = in.readLine()) != null) {
					    content.append(inputLine);
					}
					in.close();
		}
		con.disconnect();
		String inp = content.toString();
		JSONParser jsonParser = new JSONParser();
	    JSONObject obj = (JSONObject)jsonParser.parse(inp);
	    System.out.println(g.toPrettyFormat(inp));
	    g.setAccess(obj.get("access_token").toString());
        System.out.println("Access Token Replaced Successfully........");
	}
	
	public static int post(URL url,HashMap<String, Object> requestBody,TokenConfig g) throws Exception
	{
		String inputLine = g.getInputLine();
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestProperty("Authorization", inputLine);
		request.setRequestProperty("Accept", "application/json");
		request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		request.setRequestMethod("POST");
		request.setDoInput(true);
		request.setDoOutput(true);
		request.setRequestProperty("Accept-Charset", "UTF-8");	//No I18N
		StringBuffer requestParams = new StringBuffer();
		DataOutputStream dos = new DataOutputStream(request.getOutputStream());
		Iterator<String> keyIterator = requestBody.keySet().iterator();
		while(keyIterator.hasNext())
		{
			String key = keyIterator.next();
			String value = (String)requestBody.get(key);
			requestParams.append(URLEncoder.encode(key, "UTF-8"));
			requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"));
			requestParams.append("&");
		}
		dos.writeBytes(requestParams.toString());	
		dos.close();
		int status = request.getResponseCode();
		if(status==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			return(post(url,requestBody,g));
		}
		else
		{
			return(status);
		}
	}
	
	public static int delete(URL url,TokenConfig g) throws Exception
	{
		String inputLine = g.getInputLine();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("DELETE");
		con.setRequestProperty ("Authorization", inputLine);
		int response = con.getResponseCode();
		if(response==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			return(delete(url,g));
		}
		else
		{
			return response;
		}
	}
	
	public static JSONObject get(URL url,TokenConfig g) throws Exception
	{
		String inputLine = g.getInputLine();
		JSONObject obj = new JSONObject();
		StringBuffer content = new StringBuffer();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty ("Authorization", inputLine);
		int status = con.getResponseCode();
		if(status==200)
		{
			BufferedReader in = new BufferedReader(
					  new InputStreamReader(con.getInputStream()));
			while ((inputLine = in.readLine()) != null) 
			{
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			String inp = content.toString();
			JSONParser jsonParser = new JSONParser();
		    obj = (JSONObject)jsonParser.parse(inp);
		    return(obj);
		}
		else
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			return(get(url,g));
		}
	}
}
