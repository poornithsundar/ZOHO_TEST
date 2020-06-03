package zohobooks;

import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Items 
{
	static TokenConfig g = new TokenConfig();
	static String organisation_id = "715673375";
	static String base_url = "https://books.zoho.com/api/v3";
	static Scanner sc = new Scanner(System.in);
	
	public static void list_items() throws Exception
	{
		URL url = new URL(base_url+"/items?organization_id="+organisation_id);
		JSONObject obj = ApiMethods.get(url,g);
        System.out.println(obj);
        JSONArray ja = (JSONArray)obj.get("items");
        for(int i=0;i<ja.size();i++)
        {
        	JSONObject objects = (JSONObject)ja.get(i);
        	String a = objects.toString();
        	System.out.println(g.toPrettyFormat(a));
        }
	}

	private static void get_item(long id) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(base_url+"/items/"+id+"?organization_id="+organisation_id);
		JSONObject obj = ApiMethods.get(url, g);
        System.out.println(g.toPrettyFormat(obj.toString()));		
	}

	private static void delete_item(long id) throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(base_url+"/items/"+id+"?organization_id="+organisation_id);
		int response = ApiMethods.delete(url,g);
		if(response==200)
		{
			System.out.println("Item Deleted Successfully........");
		}
		else if(response==404)
		{
			System.out.println("Enter Correct Item Id....");
		}
		else
		{
			System.out.println("Item cannot be deleted.....");
		}
	}

	@SuppressWarnings("unchecked")
	private static void create_item() throws Exception {
		// TODO Auto-generated method stub
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
		int status = ApiMethods.post(url,requestBody,g);
		if(status==201)
		{
			System.out.println("Item Created Successfully....!");
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
				case 1:	ApiMethods.gen_access_token(g);
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
