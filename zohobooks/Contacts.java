package zohobooks;

import java.io.*;
import zohobooks.TokenConfig;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Contacts 
{
	static TokenConfig g = new TokenConfig();
	static String organisation_id = "715673375";
	static Scanner sc = new Scanner(System.in);
	
	public static void get_contact(long contact_id) throws IOException, ParseException
	{
		String inputLine = g.getInputLine();
		StringBuffer content = new StringBuffer();
		URL url = new URL(g.getBase_url()+"/contacts/"+contact_id+"?organization_id="+organisation_id);
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
			System.out.println("Please generate a Access Token........");
		}
		con.disconnect();
	}
	
	@SuppressWarnings("unchecked")
	public static void create_contact() throws Exception
	{
		String inputLine = g.getInputLine();
		System.out.print("Enter contact name:");
		String name = sc.nextLine();
		System.out.print("Enter company name:");
		String company = sc.nextLine();
		System.out.println("Enter Billing Address:");
		System.out.print("\tEnter Name:");
		String b_name = sc.nextLine();
		System.out.print("\tEnter Street 1 Line:");
		String b_st1 = sc.nextLine();
		System.out.print("\tEnter Street 2 Line:");
		String b_st2 = sc.nextLine();
		System.out.print("\tEnter State Code:");
		String b_stcode = sc.nextLine();
		System.out.print("\tEnter City:");
		String b_city = sc.nextLine();
		System.out.print("\tEnter State:");
		String b_state = sc.nextLine();
		System.out.print("\tEnter PIN:");
		Integer b_pin = Integer.parseInt(sc.nextLine());
		System.out.print("\tEnter Country:");
		String b_country = sc.nextLine();
		System.out.print("Is both billing and shipping addresses same?(true/false)");
		Boolean chec = Boolean.parseBoolean(sc.nextLine());
		String s_name,s_st1,s_st2,s_stcode,s_state,s_city,s_country;
		Integer s_pin;
		if(chec==false)
		{
			System.out.println("Enter Shipping Address:");
			System.out.print("\tEnter Name:");
			s_name = sc.nextLine();
			System.out.print("\tEnter Street 1 Line:");
			s_st1 = sc.nextLine();
			System.out.print("\tEnter Street 2 Line:");
			s_st2 = sc.nextLine();
			System.out.print("\tEnter State Code:");
			s_stcode = sc.nextLine();
			System.out.print("\tEnter City:");
			s_city = sc.nextLine();
			System.out.print("\tEnter State:");
			s_state = sc.nextLine();
			System.out.print("\tEnter PIN:");
			s_pin = Integer.parseInt(sc.nextLine());
			System.out.print("\tEnter Country:");
			s_country = sc.nextLine();
		}
		else
		{
			s_name = b_name;
			s_st1 = b_st1;
			s_st2 = b_st2;
			s_stcode = b_stcode;
			s_city = b_city;
			s_state = b_state;
			s_pin = b_pin;
			s_country = b_country;
		}
		JSONObject jsonObject = new JSONObject();
		JSONObject b = new JSONObject();
		JSONObject s = new JSONObject();
		b.put("attention", b_name);
		s.put("attention", s_name);
		b.put("address", b_st1);
		s.put("address", s_st1);
		b.put("street2", b_st2);
		s.put("street2", s_st2);
		b.put("state_code", b_stcode);
		s.put("state_code", s_stcode);
		b.put("city", b_city);
		s.put("city", s_city);
		b.put("state", b_state);
		s.put("state", s_state);
		b.put("zip", b_pin);
		s.put("zip", s_pin);
		b.put("country", b_country);
		s.put("country", s_country);
		jsonObject.put("contact_name", name);
		jsonObject.put("company_name", company);
		jsonObject.put("billing_address", b);
		jsonObject.put("shipping_address", s);
		HashMap<String, Object> requestBody = new HashMap<>();
		requestBody.put("JSONString", jsonObject.toString());
		URL url = new URL(g.getBase_url()+"/contacts?organization_id="+organisation_id);
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
			System.out.println("Contact Created Successfully....!");
		}
		else if(status==401)
		{
			System.out.println("Generate a new Access Token........!");
		}

		else
		{
			System.out.println("Error in input given....!");
		}
	}
	
	public static void list_contacts() throws IOException, ParseException
	{
		String inputLine = g.getInputLine();
		System.out.println(inputLine);
		StringBuffer content = new StringBuffer();
		URL url = new URL(g.getBase_url()+"/contacts?organization_id="+organisation_id);
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
	        JSONArray ja = (JSONArray)obj.get("contacts");
	        for(int i=0;i<ja.size();i++)
	        {
	        	JSONObject objects = (JSONObject)ja.get(i);
	        	String a = objects.toString();
	        	System.out.println(g.toPrettyFormat(a));
	        }
		}
		else
		{
			System.out.println("Please generate a Access Token........");
		}
		con.disconnect();
	}
	
	public static void gen_access_token()  throws IOException, InterruptedException, ParseException
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
	
	public static void delete_contact(long contact_id) throws IOException
	{
		String inputLine = g.getInputLine();
		URL url = new URL(g.getBase_url()+"/contacts/"+contact_id+"?organization_id="+organisation_id);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("DELETE");
		con.setRequestProperty ("Authorization", inputLine);
		int response = con.getResponseCode();
		if(response==200)
		{
			System.out.println("Contact Deleted Successfully........");
		}
		else if(response==404)
		{
			System.out.println("Enter Correct Contact Id....");
		}
		else if(response==401)
		{
			System.out.println("Please Generate a Access Token.......");
		}
		else
		{
			System.out.println("Contact cannot be deleted.....");
		}
	}
	
	public static String find(String name) throws IOException, ParseException
	{
		String l="0";
		String inputLine = "Zoho-oauthtoken "+g.getAccess();
		StringBuffer content = new StringBuffer();
		URL url = new URL("https://books.zoho.com/api/v3/contacts?organization_id="+organisation_id);
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
	        JSONArray ja = (JSONArray)obj.get("contacts");
	        for(int i=0;i<ja.size();i++)
	        {
	        	JSONObject objects = (JSONObject)ja.get(i);
	        	if(((String) objects.get("contact_name")).equalsIgnoreCase(name))
	        	{
	        		l = (String) objects.get("contact_id");
	        	}
	        }
	        con.disconnect();
	        return l;
		}
		else
		{
			System.out.println("Please generate a Access Token........");
			con.disconnect();
			return "-1";
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		int opt = 0;
		do
		{
			System.out.println("ZOHO BOOKS API TEST");
			System.out.println("Select any one:\n1.Generate Access Token\n2.Create a Contact\n3.Display List of Contacts\n4.Delete a Contact Using Contact Name\n5.Get Contact Details by Name\n6.Exit\nEnter your Choice:");
			opt = Integer.parseInt(sc.nextLine());
			switch(opt)
			{
				case 1:	gen_access_token();
						break;
				case 2: create_contact();
						break;
				case 3: list_contacts();
						break;
				case 4:	System.out.println("Enter Contact Name:");
						String name = sc.nextLine();
						String id = find(name);
						if(!(id.equals("0")) && !(id.equals("-1")))
						{
							delete_contact(Long.parseLong(id));
						}
						else if(id=="0")
						{
							System.out.println("Wrong Contact Name");
						}
						break;
				case 5: System.out.println("Enter Contact Name:");
						String nm = sc.nextLine();
						String i = find(nm);
						if(i!="0" && i!="-1")
						{
							get_contact(Long.parseLong(i));
						}
						else if(i=="0")
						{
							System.out.println("Wrong Contact Name");
						}
				default: System.out.println("Thank You.........!");
						 break;
			}
		}while(opt!=6);
		sc.close();
    }
}
