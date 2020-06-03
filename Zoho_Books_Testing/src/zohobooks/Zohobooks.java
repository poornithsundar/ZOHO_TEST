package zohobooks;

import java.util.Scanner;

public class Zohobooks {
	static Scanner sc2 = new Scanner(System.in);
	public static void main(String[] args) throws Exception
	{
		int opt=0;
		do{
			System.out.println("ZOHO BOOKS API:\n1.Contacts\n2.Items\n3.Exit\nEnter your Choice:");
			opt = Integer.parseInt(sc2.nextLine());
			switch(opt)
			{
				case 1: Contacts.main(new String[0]);
						break;
				case 2: Items.main(new String[0]);
						break;
				default: System.out.println("Thank You......!!");
						 break;
			}
		}while(opt!=3);
	}
}
