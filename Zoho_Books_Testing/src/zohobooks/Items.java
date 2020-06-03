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
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Items 
{
	static TokenConfig g = new TokenConfig();
	static String organisation_id = "715673375";
	static String base_url = "https://books.zoho.com/api/v3";
	static Scanner sc = new Scanner(System.in);
	
	public static void list_items() throws Exception
	{
		TokenConfig g = new TokenConfig();
		String inputLine = g.getInputLine();
		StringBuffer content = new StringBuffer();
		URL url = new URL(base_url+"/items?organization_id="+organisation_id);
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
			String inp = content.toString();
			JSONParser jsonParser = new JSONParser();
		    JSONObject obj = (JSONObject)jsonParser.parse(inp);
	        System.out.println(obj);
	        JSONArray ja = (JSONArray)obj.get("items");
	        for(int i=0;i<ja.size();i++)
	        {
	        	JSONObject objects = (JSONObject)ja.get(i);
	        	String a = objects.toString();
	        	System.out.println(g.toPrettyFormat(a));
	        }
		}
		else
		{
			System.out.println("Access Token Expired........ Generaating New Token......");
			gen_access();
			list_items();
		}
		con.disconnect();
	}

	public static void gen_access() throws IOException, InterruptedException, ParseException
	{
		Contacts.gen_access_token();
		g.setAccess(TokenConfig.prop.getProperty("access_token"));
	}

	private static void get_item(long id) throws Exception {
		// TODO Auto-generated method stub
		String inputLine = g.getInputLine();
		StringBuffer content = new StringBuffer();
		URL url = new URL(base_url+"/items/"+id+"?organization_id="+organisation_id);
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
			String inp = content.toString();
			JSONParser jsonParser = new JSONParser();
		    JSONObject obj = (JSONObject)jsonParser.parse(inp);
	        System.out.println(g.toPrettyFormat(obj.toString()));
		}
		else
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access();
			get_item(id);
		}
		con.disconnect();
	}

	private static void delete_item(long id) throws Exception {
		// TODO Auto-generated method stub
		String inputLine = g.getInputLine();
		URL url = new URL(base_url+"/items/"+id+"?organization_id="+organisation_id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("DELETE");
		con.setRequestProperty ("Authorization", inputLine);
		int response = con.getResponseCode();
		if(response==200)
		{
			System.out.println("Item Deleted Successfully........");
		}
		else if(response==404)
		{
			System.out.println("Enter Correct Item Id....");
		}
		else if(response==401)
		{
			System.out.println("Access Token Expired........! Refreshing token......!");
			gen_access();
			delete_item(id);
		}
		else
		{
			System.out.println("Item cannot be deleted.....");
		}
	}

	@SuppressWarnings("unchecked")
	private static void create_item() throws Exception {
		// TODO Auto-generated method stub
		String inputLine = g.getInputLine();
		System.out.print("Enter item name:");
		String name = sc.nextLine();
		System.out.print("Enter item rate:");
		long rate = Long.parseLong(sc.nextLine());
		System.out.println("Enter item description:");
		String desc = sc.nextLine();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", name);
		jsonObject.put("rate", rate);
		jsonObject.put("description", desc);
		HashMap<String, Object> requestBody = new HashMap<>();
		requestBody.put("JSONString", jsonObject.toString());
		URL url = new URL(base_url+"/items?organization_id="+organisation_id);
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
			System.out.println("Item Created Successfully....!");
		}
		else if(status==401)
		{
			System.out.println("Generate a new Access Token and try again........!");
		}
		else
		{
			System.out.println("Error in input given....!");
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		int opt = 0;
		do
		{
			System.out.println("ITEMS API TEST");
			System.out.println("Select any one:\n1.Generate Access Token\n2.Create a Item\n3.Display List of Items\n4.Delete a Item\n5.Get Item Details\n6.Exit\nEnter your Choice:");
			opt = Integer.parseInt(sc.nextLine());
			switch(opt)
			{
				case 1:	gen_access();
						break;
				case 2: create_item();
						break;
				case 3: list_items();
						break;
				case 4:	System.out.println("Enter Item Id:");
						long id = Long.parseLong(sc.nextLine());
						delete_item(id);
						break;
				case 5: System.out.println("Enter Item Id:");
						String nm = sc.nextLine();
						get_item(Long.parseLong(nm));
						break;
				default: System.out.println("Thank You.........!");
						 break;
			}
		}while(opt!=6);
	}
	
}
