package zohobooks;

import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;

public class Items 
{
	static TokenConfig g = new TokenConfig();
	static String organisation_id = "715673375";
	static String base_url = "https://books.zoho.com/api/v3";
	static Scanner sc = new Scanner(System.in);
	
	public static void listItems() throws Exception
	{
		URL url = new URL(base_url+"/items?organization_id="+organisation_id);
		ApiMethods.get_all(url,g,"items");
	}

	private static void getItem() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Enter Item Id:");
		long id = Long.parseLong(sc.nextLine());
		URL url = new URL(base_url+"/items/"+id+"?organization_id="+organisation_id);
		ApiMethods.get(url, g);
	}

	private static void deleteItem() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Enter Item Id:");
		long id = Long.parseLong(sc.nextLine());
		URL url = new URL(base_url+"/items/"+id+"?organization_id="+organisation_id);
		ApiMethods.delete(url,g);
	}

	@SuppressWarnings("unchecked")
	private static void createItem() throws Exception {
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
		ApiMethods.post(url,requestBody,g);
	}
	
	public void helperItems() throws Exception
	{
		int opt = 0;
		do
		{
			System.out.println("ITEMS API TEST");
			System.out.println("Select any one:\n2.Create a Item\n3.Display List of Items\n4.Delete a Item\n5.Get Item Details\n6.Exit\nEnter your Choice:");
			opt = Integer.parseInt(sc.nextLine());
			switch(opt)
			{
				case 2: createItem();
						break;
				case 3: listItems();
						break;
				case 4:	deleteItem();
						break;
				case 5: getItem();
						break;
				default: System.out.println("Thank You.........!");
						 break;
			}
		}while(opt!=6);
	}
	
}
