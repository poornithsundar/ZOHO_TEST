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
				case 1: new Contacts();
						break;
				case 2: new Items();
						break;
				default: System.out.println("Thank You......!!");
						 break;
			}
		}while(opt!=3);
	}
}
