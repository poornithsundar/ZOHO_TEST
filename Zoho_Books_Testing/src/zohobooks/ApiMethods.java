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

import org.json.simple.JSONArray;
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
	
	public static void post(URL url,HashMap<String, Object> requestBody,TokenConfig g) throws Exception
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
		if(status==201)
		{
			System.out.println("Created Successfully....!");
		}
		else if(status==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			post(url,requestBody,g);
		}
		else if(status==404)
		{
			System.out.println("Invalid Body....");
		}
		else if(status==403)
		{
			System.out.println("The user does not have enough permission or possibly not an user of the respective organization to access the resource.");
		}
		else if(status==405)
		{
			System.out.println("The requested resource does not support the HTTP method used.");
		}
		else if(status==429)
		{
			System.out.println("Too many requests within a certain time frame.");
		}
	}
	
	public static void delete(URL url,TokenConfig g) throws Exception
	{
		String inputLine = g.getInputLine();
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("DELETE");
		con.setRequestProperty ("Authorization", inputLine);
		int response = con.getResponseCode();
		if(response==200)
		{
			System.out.println("Deleted Successfully........");
		}
		else if(response==404)
		{
			System.out.println("Invalid Id....");
		}
		else if(response==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			delete(url,g);
		}
		else if(response==403)
		{
			System.out.println("The user does not have enough permission or possibly not an user of the respective organization to access the resource.");
		}
		else if(response==405)
		{
			System.out.println("The requested resource does not support the HTTP method used.");
		}
		else if(response==429)
		{
			System.out.println("Too many requests within a certain time frame.");
		}
	}
	
	public static void get(URL url,TokenConfig g) throws Exception
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
		    if(obj.size()!=0)   System.out.println(g.toPrettyFormat(obj.toString()));
		    else System.out.println("No Values");
		}
		else if(status==404)
		{
			System.out.println("Invalid Id....");
		}
		else if(status==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			get(url,g);
		}
		else if(status==403)
		{
			System.out.println("The user does not have enough permission or possibly not an user of the respective organization to access the resource.");
		}
		else if(status==405)
		{
			System.out.println("The requested resource does not support the HTTP method used.");
		}
		else if(status==429)
		{
			System.out.println("Too many requests within a certain time frame.");
		}
	}

	public static void get_all(URL url,TokenConfig g,String value) throws Exception
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
		    JSONArray ja = (JSONArray)obj.get(value);
		    if(ja.size()>0)
		    {
		        for(int i=0;i<ja.size();i++)
		        {
		        	JSONObject objects = (JSONObject)ja.get(i);
		        	String a = objects.toString();
		        	System.out.println(g.toPrettyFormat(a));
		        }
		    }
		    else System.out.println("No Values");
		}
		else if(status==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access_token(g);
			get(url,g);
		}
		else if(status==403)
		{
			System.out.println("The user does not have enough permission or possibly not an user of the respective organization to access the resource.");
		}
		else if(status==405)
		{
			System.out.println("The requested resource does not support the HTTP method used.");
		}
		else if(status==429)
		{
			System.out.println("Too many requests within a certain time frame.");
		}
	}
}
