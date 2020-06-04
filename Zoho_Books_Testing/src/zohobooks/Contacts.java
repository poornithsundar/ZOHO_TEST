package zohobooks;

import zohobooks.TokenConfig;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;


public class Contacts 
{
	
	static TokenConfig g = new TokenConfig();
	static String organisation_id = "715673375";
	static String base_url = "https://books.zoho.com/api/v3";
	static Scanner sc = new Scanner(System.in);
	
	public static void getContact() throws Exception
	{
		System.out.println("Enter Item Id:");
		long id = Long.parseLong(sc.nextLine());
		URL url = new URL(base_url+"/contacts/"+id+"?organization_id="+organisation_id);
		ApiMethods.get(url, g);
	}
	
	@SuppressWarnings("unchecked")
	public static void createContact() throws Exception
	{
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
		URL url = new URL(base_url+"/contacts?organization_id="+organisation_id);
		ApiMethods.post(url,requestBody,g);
	}
	
	public static void listContacts() throws Exception
	{
		URL url = new URL(base_url+"/contacts?organization_id="+organisation_id);
		ApiMethods.get_all(url,g,"contacts");
	}
	
	public static void deleteContact() throws Exception
	{
		System.out.println("Enter Item Id:");
		long id = Long.parseLong(sc.nextLine());
		URL url = new URL(base_url+"/contacts/"+id+"?organization_id="+organisation_id);
		ApiMethods.delete(url, g);
	}
	
	public void helperContacts() throws Exception
	{
		int opt = 0;
		do
		{
			System.out.println("CONTACTS API TEST");
			System.out.println("Select any one:\n2.Create a Contact\n3.Display List of Contacts\n4.Delete a Contact\n5.Get Contact Details\n6.Exit\nEnter your Choice:");
			opt = Integer.parseInt(sc.nextLine());
			switch(opt)
			{
				case 2: createContact();
						break;
				case 3: listContacts();
						break;
				case 4:	deleteContact();
						break;
				case 5: getContact();
						break;
				default: System.out.println("Thank You.........!");
						 break;
			}
		}while(opt!=6);
    }

}